package bg.softuni.pathfinder.model.dto;

import bg.softuni.pathfinder.model.validations.FieldMatch;
import bg.softuni.pathfinder.model.validations.UniqueEmail;
import bg.softuni.pathfinder.model.validations.UniqueUsername;
import jakarta.validation.constraints.*;

@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "Passwords don't match.")
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 5, max = 20)
    @UniqueUsername
    private String username;

    @NotBlank
    @Size(min = 5, max = 20)
    private String fullName;

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @Min(0)
    @Max(90)
    private int age;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;

    @NotBlank
    @Size(min = 5, max = 20)
    private String confirmPassword;

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
