package softuni.expirationManager.model.entities;

import jakarta.persistence.*;
import softuni.expirationManager.model.enums.RecipeType;

import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_type", nullable = false)
    private RecipeType recipeType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "ingredients_description", columnDefinition = "TEXT")
    private String ingredientsDescription;

    @Column(columnDefinition = "TEXT")
    private String preparation;

    @ManyToOne
    @JoinColumn
    private UserEntity author;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    public Long getId() {
        return id;
    }

    public RecipeEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeEntity setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeType getRecipeType() {
        return recipeType;
    }

    public RecipeEntity setRecipeType(RecipeType recipeType) {
        this.recipeType = recipeType;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RecipeEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getIngredientsDescription() {
        return ingredientsDescription;
    }

    public RecipeEntity setIngredientsDescription(String ingredientsDescription) {
        this.ingredientsDescription = ingredientsDescription;
        return this;
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeEntity setPreparation(String preparation) {
        this.preparation = preparation;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public RecipeEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public RecipeEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public RecipeEntity setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }
}
