package bg.softuni.pathfinder.user;

import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.User;
import bg.softuni.pathfinder.model.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@SessionScope
public class CurrentUser {

    private Long id;
    private String username;
    private Set<UserRole> roles;

    public String getUsername() {
        return username;
    }

    public CurrentUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public CurrentUser setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isLogged() {
        return this.id != null;
    }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(r -> r.equals(UserRole.ADMIN));
    }

    public void login(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

    public void clear() {
        this.id = null;
        this.username = null;
        this.roles = null;
    }
}
