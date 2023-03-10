package softuni.expirationManager.model.enums;

public enum RecipeType {
    SWEET("static/images/pancakes.jpg"),
    SAVORY("static/images/egg-toast.jpg");

    String defaultImageUrl;

    RecipeType(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
    }
}
