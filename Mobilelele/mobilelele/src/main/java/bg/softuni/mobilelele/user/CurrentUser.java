package bg.softuni.mobilelele.user;

import bg.softuni.mobilelele.model.dto.UserRoleViewDTO;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class CurrentUser {

    private String name;

    private boolean loggedIn;

    private List<UserRoleViewDTO> roles;

    public CurrentUser() {
        this.roles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public CurrentUser setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public CurrentUser setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        return this;
    }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN));
    }

    public List<UserRoleViewDTO> getRoles() {
        return roles;
    }

    public CurrentUser setRole(List<UserRoleViewDTO> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isAnonymous() {
        return !isLoggedIn();
    }

    public void clear() {
        this.loggedIn = false;
        this.name = null;
        this.roles = null;
    }

}
