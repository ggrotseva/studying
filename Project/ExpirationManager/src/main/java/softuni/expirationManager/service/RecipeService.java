package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.*;
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

        Set<RecipeEntity> recipes;

        if (expiredProducts.isEmpty()) {
            if (closeToExpiryProducts.isEmpty()) {
                recipes = this.recipeRepository.findFirst25ByOrderByCreatedDesc()
                        .orElse(new HashSet<>());

            } else {
                String productsRegex = closeToExpiryProducts.stream()
                        .map(ProductHomeViewDTO::getName)
                        .collect(Collectors.joining("|"));

                recipes = this.recipeRepository.findByIngredientsDescriptionMatchesRegex(productsRegex)
                        .orElseThrow();
            }
        } else {
            String productsRegex = expiredProducts.stream()
                    .map(ProductHomeViewDTO::getName)
                    .collect(Collectors.joining("|"));

            recipes = this.recipeRepository.findByIngredientsDescriptionMatchesRegex(productsRegex)
                    .orElseThrow();
        }

        return recipes.stream()
                .map(r -> this.mapper.map(r, RecipeHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    public List<RecipeBriefDTO> getAllBriefs() {
        return this.recipeRepository.findAll()
                .stream().map(r -> this.mapper.map(r, RecipeBriefDTO.class))
                .collect(Collectors.toList());
    }

    public RecipeDTO getById(Long id) {
        RecipeDTO recipe = this.mapper.map(this.recipeRepository.findById(id).orElseThrow(), RecipeDTO.class);

        String htmlFormattedIngredients = recipe.getIngredientsDescription().replace("\r\n", "<br/>");

        recipe.setIngredientsDescription(htmlFormattedIngredients);

        return recipe;
    }
}
