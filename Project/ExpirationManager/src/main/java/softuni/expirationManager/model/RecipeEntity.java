package softuni.expirationManager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> ingredients;

    @Column(columnDefinition = "TEXT")
    private String preparation;

    @ManyToOne
    @JoinColumn
    private UserEntity author;

    public Long getId() {
        return id;
    }

    public RecipeEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public RecipeEntity setIngredientList(List<String> ingredients) {
        this.ingredients = ingredients;
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
}
