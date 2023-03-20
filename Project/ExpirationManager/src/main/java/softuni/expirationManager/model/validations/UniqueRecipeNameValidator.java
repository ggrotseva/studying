package softuni.expirationManager.model.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import softuni.expirationManager.repository.RecipeRepository;

public class UniqueRecipeNameValidator implements ConstraintValidator<UniqueRecipeName, String> {

    private final RecipeRepository recipeRepository;

    public UniqueRecipeNameValidator(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.recipeRepository.findByName(value).isEmpty();
    }
}
