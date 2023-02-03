package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.UserRoleEnum;

public class UserRoleViewDTO {

    private UserRoleEnum role;

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleViewDTO setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
