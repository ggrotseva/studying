package users.models;

import org.hibernate.validator.constraints.Length;
import users.annotations.email.Email;
import users.annotations.password.Password;
import users.constants.Constants;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    @Length(min = 4, max = 30, message = Constants.USERNAME_LENGTH_MESSAGE)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false)
    @Password(containsDigit = true,
            containsLowercase = true,
            containsUppercase = true,
            containsSpecialSymbol = true)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Lob
    @Size(max = 180 * 180)
    private byte[] profilePicture;

    @Column(name = "registered_on")
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    @Column
    @Min(value = 1, message = Constants.AGE_TOO_LOW)
    @Max(value = 120, message = Constants.AGE_TOO_HIGH)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "born_town_id", referencedColumnName = "id")
    private Town bornTown;

    @ManyToOne
    @JoinColumn(name = "current_town_id", referencedColumnName = "id")
    private Town currentTown;

    @ManyToMany()
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    List<User> friends;

    @OneToMany(mappedBy = "user")
    private List<Album> albums;

    @Column
    private boolean isDeleted;

    public User() {
    }

    public User(String username, String password, String email) {
        this();

        setUsername(username);
        setPassword(password);
        setEmail(email);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    public Town getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Town currentTown) {
        this.currentTown = currentTown;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // --------------------------------------------------------------------------------------------------------

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public User user;

        private Builder() {
            this.user = new User();
        }

        public Builder withUsername(String username) {
            user.setUsername(username);
            return this;
        }

        public Builder withFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder withLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder withPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder withEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder withBornTown(Town bornTown) {
            user.setBornTown(bornTown);
            return this;
        }

        public Builder withCurrentTown(Town currentTown) {
            user.setCurrentTown(currentTown);
            return this;
        }

        public Builder withAge(int age) {
            user.setAge(age);
            return this;
        }

        public Builder withRegisterDate(LocalDateTime registerDate) {
            user.setRegisteredOn(registerDate);
            return this;
        }

        public Builder withLastLoginTime(LocalDateTime lastLoginTime) {
            user.setLastTimeLoggedIn(lastLoginTime);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
