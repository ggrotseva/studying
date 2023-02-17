package bg.softuni.battleship.util.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueShipNameValidator.class)
public @interface UniqueShipName {

    String message() default "This ship name is already used.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
