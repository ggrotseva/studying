package softuni.expirationManager.model.dtos.recipe;

public class RecipeDTO {

    private Long id;
    private String name;
    private String imageUrl;
    private String ingredientsDescription;
    private String preparation;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public RecipeDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RecipeDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getIngredientsDescription() {
        return ingredientsDescription;
    }

    public RecipeDTO setIngredientsDescription(String ingredientsDescription) {
        this.ingredientsDescription = ingredientsDescription;
        return this;
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeDTO setPreparation(String preparation) {
        this.preparation = preparation;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public RecipeDTO setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }
}
