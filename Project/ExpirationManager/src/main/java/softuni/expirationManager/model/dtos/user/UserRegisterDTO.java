package softuni.expirationManager.model.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import softuni.expirationManager.model.validations.FieldMatch;
import softuni.expirationManager.model.validations.UniqueEmail;
import softuni.expirationManager.model.validations.UniqueUsername;

@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "Passwords don't match")
public class UserRegisterDTO {

    @Size(min = 2, max = 50, message = "First Name should be between 2 and 50 characters long.")
    @NotNull(message = "First Name is required")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last Name should be between 2 and 50 characters long.")
    @NotNull(message = "Last Name is required")
    private String lastName;

    @Size(min = 4, max = 50, message = "Username should be between 4 and 50 characters long.")
    @NotNull(message = "Username is required")
    @UniqueUsername
    private String username;

    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Email is required")
    @UniqueEmail
    private String email;

    @Size(min = 5, max = 50, message = "Password should be between 5 and 50 characters long.")
    @NotNull(message = "Password is required")
    private String password;

//    @Size(min = 5, max = 50, message = "Password should be between 5 and 50 characters long.")
    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
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
