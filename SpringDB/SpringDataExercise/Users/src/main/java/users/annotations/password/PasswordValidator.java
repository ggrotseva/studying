package users.annotations.password;

import users.annotations.ValidationUtil;
import users.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, CharSequence> {

    private static final Pattern HAS_DIGIT = Pattern.compile("\\d");
    private static final Pattern HAS_UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_SPECIAL_CHARACTER = Pattern.compile("[!@#$%^&*,_+<>?]");

    private int minLength;
    private int maxLength;
    private boolean hasDigit;
    private boolean hasUppercase;
    private boolean hasLowercase;
    private boolean hasSpecialSymbol;

    @Override
    public void initialize(Password password) {
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.hasDigit = password.containsDigit();
        this.hasUppercase = password.containsUppercase();
        this.hasLowercase = password.containsLowercase();
        this.hasSpecialSymbol = password.containsSpecialSymbol();
    }


    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            ValidationUtil.setErrorMessage(context, Constants.INVALID_PASSWORD_NULL);
            return false;
        }

        String password = value.toString();

        if (password.length() < this.minLength) {
            ValidationUtil.setErrorMessage(context, Constants.PASSWORD_TOO_SHORT);
            return false;
        }

        if (password.length() > this.maxLength) {
            ValidationUtil.setErrorMessage(context, Constants.PASSWORD_TOO_LONG);
            return false;
        }

        if (hasDigit && !HAS_DIGIT.matcher(password).find()) {
            ValidationUtil.setErrorMessage(context, Constants.PASSWORD_DIGIT_ERROR);
            return false;
        }

        if (hasLowercase && !HAS_LOWERCASE.matcher(password).find()) {
            ValidationUtil.setErrorMessage(context, Constants.PASSWORD_LOWER_ERROR);
            return false;
        }

        if (hasUppercase && !HAS_UPPERCASE.matcher(password).find()) {
            ValidationUtil.setErrorMessage(context, Constants.PASSWORD_UPPER_ERROR);
            return false;
        }

        if (hasSpecialSymbol && !HAS_SPECIAL_CHARACTER.matcher(password).find()) {
            ValidationUtil.setErrorMessage(context, Constants.PASSWORD_SPECIAL_SYMBOL_ERROR);
            return false;
        }

        return true;
    }
}
