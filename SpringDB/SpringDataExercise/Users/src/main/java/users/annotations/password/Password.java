package users.annotations.password;

import org.springframework.stereotype.Component;
import users.constants.Constants;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Password {

    String message() default Constants.INVALID_PASSWORD;

    String regex() default Constants.PASSWORD_VALIDATION_REGEX;

    int minLength() default 6;

    int maxLength() default 50;

    boolean containsDigit() default false;

    boolean containsUppercase() default false;

    boolean containsLowercase() default false;

    boolean containsSpecialSymbol() default false;

}
