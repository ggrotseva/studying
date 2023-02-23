package bg.softuni.mobilelele.user;

import bg.softuni.mobilelele.model.dto.UserRoleViewDTO;
import bg.softuni.mobilelele.model.entities.User;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScope
public class CurrentUser {

    private Long id;
    private String name;
    private List<UserRoleViewDTO> roles;

    public CurrentUser() {
        this.roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public CurrentUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CurrentUser setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN));
    }

    public List<UserRoleViewDTO> getRoles() {
        return roles;
    }

    public CurrentUser setRoles(List<UserRoleViewDTO> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isLoggedIn() {
        return this.id != null;
    }

    public void login(User user) {
        this.id = user.getId();
        this.name = user.getFirstName();
        this.roles = user.getUserRoles().stream()
                .map(r -> new UserRoleViewDTO().setRole(r.getRole()))
                .collect(Collectors.toList());
    }

    public void logout() {
        this.id = null;
        this.name = null;
        this.roles.clear();
    }
}
