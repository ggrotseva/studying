package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.recipe.*;
import softuni.expirationManager.model.entities.RecipeEntity;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.repository.RecipeRepository;
import softuni.expirationManager.repository.RecipeSearchSpecification;
import softuni.expirationManager.repository.UserRepository;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.utils.DateTimeProvider;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final DateTimeProvider dateTimeProvider;
    private final ModelMapper mapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         UserRepository userRepository,
                         CloudinaryService cloudinaryService,
                         DateTimeProvider dateTimeProvider,
                         ModelMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.dateTimeProvider = dateTimeProvider;
        this.mapper = mapper;
    }

    public boolean authorizeActions(MyUserDetails userDetails, Long recipeId) {
        Long recipeAuthorId = this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND))
                .getAuthor().getId();

        return userDetails.getId().equals(recipeAuthorId)
                || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public void addRecipe(RecipeAddDTO recipeAddDTO, String username) {

        RecipeEntity recipe = this.mapper.map(recipeAddDTO, RecipeEntity.class);

        UserEntity author = this.userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException(Constants.NO_USER_FOUND));

        recipe.setAuthor(author)
                .setCreated(this.dateTimeProvider.getDateTimeNow())
                .setModified(this.dateTimeProvider.getDateTimeNow());

        if (recipeAddDTO.getImage() == null || recipeAddDTO.getImage().isEmpty()) {
            recipe.setImageUrl(recipeAddDTO.getType().getDefaultImageUrl());
        } else {
            String imageUrl = this.cloudinaryService.saveImageToCloudinary(recipeAddDTO.getImage());
            recipe.setImageUrl(imageUrl);
        }

        this.recipeRepository.saveAndFlush(recipe);
    }

    public void editRecipe(RecipeEditDTO recipeEditDTO) {

        RecipeEntity recipe = this.recipeRepository.findById(recipeEditDTO.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND));

        handleImageUrlEdit(recipe, recipeEditDTO);

        recipe = recipe.setName(recipeEditDTO.getName())
                .setRecipeType(recipeEditDTO.getType())
                .setIngredientsDescription(recipeEditDTO.getIngredientsDescription())
                .setPreparation(recipeEditDTO.getPreparation())
                .setModified(this.dateTimeProvider.getDateTimeNow());

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

    public Page<RecipeBriefDTO> getAllRecipeBriefs(Pageable pageable) {
        return this.recipeRepository.findAllByOrderByCreatedDesc(pageable)
                .map(r -> this.mapper.map(r, RecipeBriefDTO.class));
    }

    public Page<RecipeBriefDTO> searchRecipes(RecipeSearchDTO recipeSearchDTO, Pageable pageable) {
        return this.recipeRepository.findAll(new RecipeSearchSpecification(recipeSearchDTO), pageable)
                .map(r -> this.mapper.map(r, RecipeBriefDTO.class));
    }

    @Cacheable("recipeIdeas")
    public List<RecipeHomeViewDTO> getRecipeIdeas(List<ProductHomeViewDTO> expiredProducts,
                                                  List<ProductHomeViewDTO> closeToExpiryProducts) {

        Set<String> productsToBeUsed = new LinkedHashSet<>();

        expiredProducts.stream().map(ProductHomeViewDTO::getName).forEach(productsToBeUsed::add);
        closeToExpiryProducts.stream().map(ProductHomeViewDTO::getName).forEach(productsToBeUsed::add);

        List<RecipeHomeViewDTO> recipeIdeas = new ArrayList<>();

        if (!productsToBeUsed.isEmpty()) {
            recipeIdeas.addAll(extractRecipesFromProducts(productsToBeUsed));
        }

        if (recipeIdeas.isEmpty()) {
            recipeIdeas = this.recipeRepository.findFirst25ByOrderByCreatedDesc()
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_RECIPE_FOUND))
                    .stream()
                    .map(r -> this.mapper.map(r, RecipeHomeViewDTO.class))
                    .toList();
        }

        return recipeIdeas;
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
            String imageUrl = this.cloudinaryService.saveImageToCloudinary(recipeEditDTO.getImage());
            recipe.setImageUrl(imageUrl);
        }
    }

    private List<RecipeHomeViewDTO> extractRecipesFromProducts(Collection<String> products) {

        String productsRegex = String.join("|", products);

        return this.recipeRepository.findByIngredientsDescriptionMatchesRegex(productsRegex)
                .orElse(new HashSet<>())
                .stream()
                .map(r -> this.mapper.map(r, RecipeHomeViewDTO.class)
                        .setProducts(extractIncludedProducts(r, productsRegex)))
                .toList();
    }

    private String extractIncludedProducts(RecipeEntity recipe, String productsRegex) {

        Set<String> matched = new HashSet<>();

        Pattern pattern = Pattern.compile(productsRegex);
        Matcher matcher = pattern.matcher(recipe.getIngredientsDescription());

        while (matcher.find()) {
            String match = matcher.group();
            matched.add(match);
        }

        return String.join("; ", matched);
    }
}
