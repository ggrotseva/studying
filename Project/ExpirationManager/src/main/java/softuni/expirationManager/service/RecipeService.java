package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
    private final ModelMapper mapper;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, ModelMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void createRecipe(RecipeAddDTO recipeAddDTO, String username) {

        if (recipeAddDTO.getImageUrl() == null || recipeAddDTO.getImageUrl().isEmpty()) {
            recipeAddDTO.setImageUrl(recipeAddDTO.getType().getDefaultImageUrl());
        }

        RecipeEntity recipe = this.mapper.map(recipeAddDTO, RecipeEntity.class);

        UserEntity author = this.userRepository.findByUsername(username).orElseThrow();

        recipe.setAuthor(author)
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now());

        this.recipeRepository.saveAndFlush(recipe);
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

    public List<RecipeBriefDTO> getAllRecipeBriefs() {
        return this.recipeRepository.findAllByOrderByCreatedDesc().orElseThrow()
                .stream().map(r -> this.mapper.map(r, RecipeBriefDTO.class))
                .collect(Collectors.toList());
    }

    public RecipeDTO getRecipeDtoById(Long id) {
        RecipeDTO recipe = this.mapper.map(this.recipeRepository.findById(id).orElseThrow(), RecipeDTO.class);

        String htmlFormattedIngredients = recipe.getIngredientsDescription().replace(System.lineSeparator(), "<br/>");
        String htmlFormattedPreparation = recipe.getPreparation().replace(System.lineSeparator(), "<br/>");

        recipe.setIngredientsDescription(htmlFormattedIngredients);
        recipe.setPreparation(htmlFormattedPreparation);

        return recipe;
    }

    public RecipeEditDTO getRecipeEditDtoById(Long id) {
        RecipeEditDTO recipeEditDTO = this.mapper.map(this.recipeRepository.findById(id).orElseThrow(), RecipeEditDTO.class);

        if (recipeEditDTO.getImageUrl().equals(recipeEditDTO.getType().getDefaultImageUrl())) {
            recipeEditDTO.setImageUrl("");
        }

        return recipeEditDTO;
    }

    public void editRecipe(RecipeEditDTO recipeEditDTO) {

        RecipeEntity recipe = this.recipeRepository.findById(recipeEditDTO.getId()).orElseThrow();

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

    private void handleImageUrlEdit(RecipeEntity recipe, RecipeEditDTO recipeEditDTO) {
        if (!recipeEditDTO.getType().equals(recipe.getRecipeType())) {
            if (recipeEditDTO.getImageUrl() == null || recipeEditDTO.getImageUrl().isEmpty()) {
                recipe.setImageUrl(recipeEditDTO.getType().getDefaultImageUrl());
            } else {
                recipe.setImageUrl(recipeEditDTO.getImageUrl());
            }
        } else {
            if (recipeEditDTO.getImageUrl() != null && !recipeEditDTO.getImageUrl().isEmpty()) {
                recipe.setImageUrl(recipeEditDTO.getImageUrl());
            }
        }
    }
}
