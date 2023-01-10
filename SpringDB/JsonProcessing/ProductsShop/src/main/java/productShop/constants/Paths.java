package productShop.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path CATEGORIES_PATH =
            Path.of("D:/Java/Spring/JsonProcessing/ProductsShop/src/main/resources/dbContent/categories.json");

    public static final Path USERS_PATH =
            Path.of("D:/Java/Spring/JsonProcessing/ProductsShop/src/main/resources/dbContent/users.json");

    public static final Path PRODUCTS_PATH =
            Path.of("D:/Java/Spring/JsonProcessing/ProductsShop/src/main/resources/dbContent/products.json");

    public static final Path PRODUCTS_WITH_NO_BUYER_JSON =
            Path.of("D:/Java/Spring/JsonProcessing/ProductsShop/src/main/resources/outputs/products-in-range.json");

    public static final Path CATEGORIES_BY_PRODUCTS_JSON =
            Path.of("D:/Java/Spring/JsonProcessing/ProductsShop/src/main/resources/outputs/categories-by-products.json");

    public static final Path USERS_WITH_SOLD_PRODUCT_JSON =
            Path.of("D:/Java/Spring/JsonProcessing/ProductsShop/src/main/resources/outputs/users-sold-products.json");
}
