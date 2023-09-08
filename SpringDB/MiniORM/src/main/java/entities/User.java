package entities;

import orm.annotations.*;

import java.time.LocalDate;

@Entity(name = "users")
public class User {

    @Id
    private long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private LocalDate registration;

    @Column(name = "user_role")
    private String role;

    public User(String username, int age, LocalDate registration, String role) {
        this.username = username;
        this.age = age;
        this.registration = registration;
        this.role = role;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Username: " + this.getUsername() +
                ", Id: " + this.getId() +
                ", Age: " + this.getAge() +
                ", RegDate: " + this.getRegistration() +
                ", Role: " + this.role;
    }
}
