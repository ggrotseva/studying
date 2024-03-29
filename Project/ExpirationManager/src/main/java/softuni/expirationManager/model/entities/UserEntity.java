package softuni.expirationManager.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id", referencedColumnName = "id"))
    private List<UserRoleEntity> userRoles;

    @Column(name="is_subscribed")
    private boolean isSubscribed;

    public UserEntity() {
        this.userRoles = new ArrayList<>();
    }

    public UserEntity(String username, String firstName,
                      String lastName, String email,
                      String password, List<UserRoleEntity> userRoles,
                      boolean isSubscribed) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
        this.isSubscribed = isSubscribed;
    }

    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return Collections.unmodifiableList(userRoles);
    }

    public UserEntity setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public UserEntity setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
        return this;
    }

    public void addRole(UserRoleEntity userRole) {
        this.userRoles.add(userRole);
    }

    public void removeRole(UserRoleEntity userRole) {
        this.userRoles.remove(userRole);
    }
}
