package bg.softuni.battleship.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 3, max = 10,  message = "The username must be between 3 and 10 characters long.")
    private String username;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 3,  message = "Password must be at least 3 characters long.")
    private String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
