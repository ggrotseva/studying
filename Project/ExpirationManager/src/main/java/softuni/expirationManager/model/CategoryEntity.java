package softuni.expirationManager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public CategoryEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public CategoryEntity setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CategoryEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
