package users.annotations;

import javax.validation.ConstraintValidatorContext;

public class ValidationUtil {
    ;

    public static void setErrorMessage(ConstraintValidatorContext context, String errorMessage) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    }
}
