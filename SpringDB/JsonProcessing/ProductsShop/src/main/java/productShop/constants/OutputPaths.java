package productShop.constants;

import java.nio.file.Path;

public enum OutputPaths {
    ;

    public static final Path PRODUCTS_WITH_NO_BUYER_JSON =
            Path.of("ProductsShop/src/main/resources/outputs/products-in-range.json");

    public static final Path CATEGORIES_BY_PRODUCTS_JSON =
            Path.of("ProductsShop/src/main/resources/outputs/categories-by-products.json");

    public static final Path USERS_WITH_SOLD_PRODUCT_JSON =
            Path.of("ProductsShop/src/main/resources/outputs/users-sold-products.json");

    public static final Path USERS_AND_PRODUCTS_JSON =
            Path.of("ProductsShop/src/main/resources/outputs/users-and-products.json");
}
