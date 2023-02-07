package bg.softuni.pathfinder.user;

import bg.softuni.pathfinder.model.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class CurrentUser {

    private String username;

    private boolean isLogged;

    private List<UserRole> roles;

    public String getUsername() {
        return username;
    }

    public CurrentUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public CurrentUser setLogged(boolean logged) {
        isLogged = logged;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public CurrentUser setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public void clear() {
        this.username = null;
        this.isLogged = false;
        this.roles = null;
    }
}
