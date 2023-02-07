package bg.softuni.pathfinder.user;

import bg.softuni.pathfinder.model.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Set;

@Component
@SessionScope
public class CurrentUser {

    private String username;

    private boolean isLogged;

    private Set<UserRole> roles;

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

    public Set<UserRole> getRoles() {
        return roles;
    }

    public CurrentUser setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(r -> r.equals(UserRole.ADMIN));
    }

    public void clear() {
        this.username = null;
        this.isLogged = false;
        this.roles = null;
    }
}
