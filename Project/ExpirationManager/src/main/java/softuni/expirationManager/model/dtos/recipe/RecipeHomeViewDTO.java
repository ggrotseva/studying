package softuni.expirationManager.model.dtos.recipe;

import softuni.expirationManager.model.enums.RecipeType;

public class RecipeHomeViewDTO {

    private Long id;
    private String name;
    private RecipeType type;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public RecipeHomeViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeHomeViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeType getType() {
        return type;
    }

    public RecipeHomeViewDTO setType(RecipeType type) {
        this.type = type;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RecipeHomeViewDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
