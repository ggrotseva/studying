package softuni.expirationManager.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import softuni.expirationManager.model.enums.RecipeType;

public class RecipeAddDTO {

    @NotBlank(message = "Recipe Name is required")
    private String name;

    @NotNull(message = "Recipe Type is required")
    private RecipeType type;

    private String imageUrl;

    @NotBlank(message = "Recipe ingredients are required")
    private String ingredientsDescription;

    @NotBlank(message = "Preparation description is required")
    private String preparation;

    public String getName() {
        return name;
    }

    public RecipeAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeType getType() {
        return type;
    }

    public RecipeAddDTO setType(RecipeType type) {
        this.type = type;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RecipeAddDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getIngredientsDescription() {
        return ingredientsDescription;
    }

    public RecipeAddDTO setIngredientsDescription(String ingredientsDescription) {
        this.ingredientsDescription = ingredientsDescription;
        return this;
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeAddDTO setPreparation(String preparation) {
        this.preparation = preparation;
        return this;
    }
}
