package bg.softuni.battleship.models.dto;

import bg.softuni.battleship.util.validations.UniqueEmail;
import bg.softuni.battleship.util.validations.UniqueUsername;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {

    @UniqueUsername
    @NotNull
    @Size(min = 3, max = 10, message = "The username must be between 3 and 10 characters long.")
    private String username;

    @NotNull
    @Size(min = 5, max = 20, message = "Full name length must be between 5 and 20 characters long.")
    private String fullName;

    @Email(message = "Enter a valid email address.")
    @NotEmpty(message = "Enter a valid email address.")
    @UniqueEmail
    private String email;

    @NotNull
    @Size(min = 3, message = "Password must be at least 3 characters long.")
    private String password;

    @NotNull
    @Size(min = 3, message = "Password must be at least 3 characters long.")
    private String confirmPassword;

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegisterDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
