package softuni.expirationManager.model.dtos.user;

public class UserProfileDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isSubscribed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public UserProfileDTO setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
        return this;
    }
}
