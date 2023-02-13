package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.validation.ValidateLoginUser;
import jakarta.validation.constraints.NotBlank;

@ValidateLoginUser
public class UserLoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "username='" + username + '\'' +
                ", password=" + (password != null ? "[PROVIDED]" : null) +
                '}';
    }
}

