package bg.softuni.pathfinder.model.dto;

import bg.softuni.pathfinder.model.enums.Level;

public class UserDetailsDTO {

    private Long id;
    private String username;
    private String fullName;
    private int age;
    private Level level;

    public Long getId() {
        return id;
    }

    public UserDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDetailsDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDetailsDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserDetailsDTO setAge(int age) {
        this.age = age;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public UserDetailsDTO setLevel(Level level) {
        this.level = level;
        return this;
    }
}
