package softuni.expirationManager.model.enums;

public enum RecipeType {
    SWEET("/images/pancakes.jpg"),
    SAVORY("/images/egg-toast.jpg");

    private final String defaultImageUrl;

    RecipeType(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
    }

    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }
}
