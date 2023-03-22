package softuni.expirationManager.model.dtos.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import softuni.expirationManager.model.enums.RecipeType;
import softuni.expirationManager.model.validations.FileSize;

public class RecipeAddDTO {

    @NotBlank(message = "Recipe Name is required")
    private String name;

    @NotNull(message = "Recipe Type is required")
    private RecipeType type;

    @FileSize(maxSizeInKilobytes = "2048")
    private MultipartFile image;

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

    public MultipartFile getImage() {
        return image;
    }

    public RecipeAddDTO setImage(MultipartFile image) {
        this.image = image;
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
