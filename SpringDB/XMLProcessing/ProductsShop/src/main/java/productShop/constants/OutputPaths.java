package productShop.constants;

import java.nio.file.Path;

public enum OutputPaths {
    ;

    public static final Path PRODUCTS_WITH_NO_BUYER_XML =
            Path.of("ProductsShop/src/main/resources/outputs/products-in-range.xml");

    public static final Path CATEGORIES_BY_PRODUCTS_XML =
            Path.of("ProductsShop/src/main/resources/outputs/categories-by-products.xml");

    public static final Path USERS_WITH_SOLD_PRODUCT_XML =
            Path.of("ProductsShop/src/main/resources/outputs/users-sold-products.xml");

    public static final Path USERS_AND_PRODUCTS_XML =
            Path.of("ProductsShop/src/main/resources/outputs/users-and-products.xml");
}
