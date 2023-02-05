package bg.softuni.mobilelele.user;

import bg.softuni.mobilelele.model.dto.UserRoleViewDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class CurrentUser {

    private String name;

    private boolean loggedIn;

    private boolean isAdmin;

    private List<UserRoleViewDTO> roles;

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

    public CurrentUser setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
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
        this.isAdmin = false;
    }

}
