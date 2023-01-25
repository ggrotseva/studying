package bg.softuni.mobilelele.model.user.dto;

import bg.softuni.mobilelele.model.userRole.UserRole;

public class UserRegisterDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private UserRole role;

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
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

    public UserRole getRole() {
        return role;
    }

    public UserRegisterDTO setRole(UserRole role) {
        this.role = role;
        return this;
    }
}
