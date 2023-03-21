package softuni.expirationManager.model.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MultipartFileValidator.class)
public @interface FileSizeValidator {

    String maxSizeInKilobytes();

    String message() default "File is too big.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
