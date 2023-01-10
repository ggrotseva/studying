package users.annotations.email;

import org.springframework.stereotype.Component;
import users.constants.Constants;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default Constants.INVALID_EMAIL_FORMAT;

    String regex() default Constants.EMAIL_VALIDATION_REGEX;

}
