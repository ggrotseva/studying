package users.annotations.email;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements ConstraintValidator<Email, CharSequence> {

    // fields
    private Pattern validationPattern;

    @Override
    public void initialize(Email email) {
        // set fields to "email" fields
        this.validationPattern = Pattern.compile(email.regex());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        String email = value.toString();

        return this.validationPattern.matcher(email).matches();
    }
}
