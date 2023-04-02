package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.expirationManager.repository.RecipeSpecification;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.recipe.*;
import softuni.expirationManager.model.entities.RecipeEntity;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.repository.RecipeRepository;
import softuni.expirationManager.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ImageCloudService imageCloudService;
    private final ModelMapper mapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         UserRepository userRepository,
                         ImageCloudService imageCloudService,
                         ModelMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.imageCloudService = imageCloudService;
        this.mapper = mapper;
    }

    public boolean authorizeActions(MyUserDetails userDetails, Long recipeId) {
        Long categoryUserId = this.recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND))
                .getAuthor().getId();

        return userDetails.getId().equals(categoryUserId)
                || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public void addRecipe(RecipeAddDTO recipeAddDTO, String username) {

        RecipeEntity recipe = this.mapper.map(recipeAddDTO, RecipeEntity.class);

        UserEntity author = this.userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException(Constants.NO_USER_FOUND));

        recipe.setAuthor(author)
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now());

        if (recipeAddDTO.getImage() == null || recipeAddDTO.getImage().isEmpty()) {
            recipe.setImageUrl(recipeAddDTO.getType().getDefaultImageUrl());
        } else {
            String imageUrl = this.imageCloudService.saveImage(recipeAddDTO.getImage());
            recipe.setImageUrl(imageUrl);
        }

        this.recipeRepository.saveAndFlush(recipe);
    }

    public void editRecipe(RecipeEditDTO recipeEditDTO) {

        RecipeEntity recipe = this.recipeRepository.findById(recipeEditDTO.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND));

        handleImageUrlEdit(recipe, recipeEditDTO);

        recipe.setName(recipeEditDTO.getName())
                .setRecipeType(recipeEditDTO.getType())
                .setIngredientsDescription(recipeEditDTO.getIngredientsDescription())
                .setPreparation(recipeEditDTO.getPreparation())
                .setModified(LocalDateTime.now());

        this.recipeRepository.saveAndFlush(recipe);
    }

    public void deleteById(Long id) {
        this.recipeRepository.deleteById(id);
    }

    public RecipeEditDTO getRecipeEditDtoById(Long id) {
        return this.mapper.map(this.recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND)), RecipeEditDTO.class);
    }

    public RecipeDTO getRecipeDtoById(Long id) {
        RecipeDTO recipe = this.mapper.map(this.recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND)), RecipeDTO.class);

        String htmlFormattedIngredients = recipe.getIngredientsDescription().replace(System.lineSeparator(), "<br/>");
        String htmlFormattedPreparation = recipe.getPreparation().replace(System.lineSeparator(), "<br/>");

        recipe.setIngredientsDescription(htmlFormattedIngredients);
        recipe.setPreparation(htmlFormattedPreparation);

        return recipe;
    }

    public Page<RecipeBriefDTO> getRecipesByAuthor(Long authorId, Pageable pageable) {
        return this.recipeRepository.findAllByAuthorIdOrderByCreatedDesc(authorId, pageable)
                .map(r -> this.mapper.map(r, RecipeBriefDTO.class));
    }

    private void handleImageUrlEdit(RecipeEntity recipe, RecipeEditDTO recipeEditDTO) {
        // if user doesn't upload an image
        if (recipeEditDTO.getImage() == null || recipeEditDTO.getImage().isEmpty()) {
            // only if existing recipe has the default imageUrl and RecipeType is changed, imageUrl is changed too
            if (recipe.getRecipeType().getDefaultImageUrl().equals(recipe.getImageUrl())
                    && recipe.getRecipeType() != recipeEditDTO.getType()) {

                recipe.setImageUrl(recipeEditDTO.getType().getDefaultImageUrl());
            } // else nothing changes
        } else {
            // if user uploads new image
            String imageUrl = this.imageCloudService.saveImage(recipeEditDTO.getImage());
            recipe.setImageUrl(imageUrl);
        }
    }

    public List<RecipeHomeViewDTO> getRecipeIdeas(List<ProductHomeViewDTO> expiredProducts,
                                                  List<ProductHomeViewDTO> closeToExpiryProducts) {

        Set<RecipeEntity> recipes = new HashSet<>();

        if (!expiredProducts.isEmpty()) {

            String productsRegex = expiredProducts.stream()
                    .map(ProductHomeViewDTO::getName)
                    .collect(Collectors.joining("|"));

            recipes = this.recipeRepository.findByIngredientsDescriptionMatchesRegex(productsRegex)
                    .orElse(new HashSet<>());

        } else {
            if (!closeToExpiryProducts.isEmpty()) {

                String productsRegex = closeToExpiryProducts.stream()
                        .map(ProductHomeViewDTO::getName)
                        .collect(Collectors.joining("|"));

                recipes = this.recipeRepository.findByIngredientsDescriptionMatchesRegex(productsRegex)
                        .orElse(new HashSet<>());
            }

        }

        if (recipes.isEmpty()) {
            recipes = this.recipeRepository.findFirst25ByOrderByCreatedDesc()
                    .orElse(new HashSet<>());
        }

        return recipes.stream()
                .map(r -> this.mapper.map(r, RecipeHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    public Page<RecipeBriefDTO> getAllRecipeBriefs(Pageable pageable) {
        return this.recipeRepository.findAllByOrderByCreatedDesc(pageable)
                .map(r -> this.mapper.map(r, RecipeBriefDTO.class));
    }

    public Page<RecipeBriefDTO> searchRecipes(RecipeSearchDTO recipeSearchDTO, Pageable pageable) {
        return this.recipeRepository.findAll(new RecipeSpecification(recipeSearchDTO), pageable)
                .map(r -> this.mapper.map(r, RecipeBriefDTO.class));
    }
}
