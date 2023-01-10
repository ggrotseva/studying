package GameStore.domain.dtos;

import static GameStore.constants.Validations.*;

public class UserRegisterDTO {

    private final String email;
    private final String password;
    private final String confirmPassword;
    private final String fullName;

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }


    private void validate() {
        final boolean isEmailValid = this.email.matches(EMAIL_PATTERN);

        if (!isEmailValid) {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }

        final boolean isPasswordValid = this.password.matches(PASSWORD_PATTERN);

        if (!isPasswordValid) {
            throw new IllegalArgumentException(INVALID_PASSWORD_FORMAT_MESSAGE);
        }

        final boolean isPasswordConfirmed = this.confirmPassword.equals(this.password);

        if (!isPasswordConfirmed) {
            throw new IllegalArgumentException(PASSWORD_CONFIRM_FAILED_MESSAGE);
        }
    }

    public String successfulRegisterMessage() {
        return this.fullName + " was registered";
    }
}
