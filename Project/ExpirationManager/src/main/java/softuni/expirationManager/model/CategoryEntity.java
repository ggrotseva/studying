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

    @Lob
    private byte[] icon;

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

    public byte[] getIcon() {
        return icon;
    }

    public CategoryEntity setIcon(byte[] icon) {
        this.icon = icon;
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
