package softuni.expirationManager.utils;

import java.util.Map;

public enum Constants {
    ;

    public static final String PAGE_NOT_FOUND = "404 Page not found!";
    public static final String ACCESS_DENIED = "Access denied!";
    public static final String REQUEST_SIZE_EXCEEDED = "File you uploaded exceeds maximum allowed size";

    public static final String NO_CATEGORY_FOUND = "No such category found!";
    public static final String NO_PRODUCT_FOUND = "No such product found!";
    public static final String NO_RECIPE_FOUND = "No such recipe found!";
    public static final String NO_USER_FOUND = "No such user found!";
    public static final String NO_ROLE_FOUND = "No such role found!";

    public static final String DEFAULT_ICON_PATH = "src/main/resources/static/images/jar-of-jam.png";

    public final static Map<String, String> INITIAL_CATEGORIES = Map.of(
            "cans", "chickpeas, beans, coconut milk, etc.",
            "grains", "pasta, flours, oats, rice, etc.",
            "jars", "jams, tahini, honey, pickles, etc.",
            "sweets", "biscuits, pralines, chocolates, etc.");
}
