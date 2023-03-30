package softuni.expirationManager.model.dtos.recipe;

import softuni.expirationManager.model.enums.RecipeType;

import java.util.List;

public class RecipeSearchDTO {

    private String search;

    private List<RecipeType> types;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<RecipeType> getTypes() {
        return types;
    }

    public void setType(List<RecipeType> types) {
        this.types = types;
    }
}
