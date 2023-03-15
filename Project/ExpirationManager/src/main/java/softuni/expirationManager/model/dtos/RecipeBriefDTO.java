package softuni.expirationManager.model.dtos;

import softuni.expirationManager.model.enums.RecipeType;

import java.time.LocalDateTime;

public class RecipeBriefDTO {

    private Long id;
    private String name;
    private RecipeType type;
    private String imageUrl;
    private String preparation;
    private String authorUsername;
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public RecipeBriefDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeBriefDTO setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeType getType() {
        return type;
    }

    public RecipeBriefDTO setType(RecipeType type) {
        this.type = type;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RecipeBriefDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getPreparation() {
        return preparation;
    }

    public RecipeBriefDTO setPreparation(String preparation) {
        this.preparation = preparation;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public RecipeBriefDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public RecipeBriefDTO setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
