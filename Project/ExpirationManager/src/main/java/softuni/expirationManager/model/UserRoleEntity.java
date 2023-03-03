package softuni.expirationManager.model;

import jakarta.persistence.*;
import softuni.expirationManager.model.enums.UserRoleEnum;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, unique = true)
    private UserRoleEnum userRole;

    public Long getId() {
        return id;
    }

    public UserRoleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRoleEntity setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
