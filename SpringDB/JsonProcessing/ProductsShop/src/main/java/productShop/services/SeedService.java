package productShop.services;

import java.io.IOException;

public interface SeedService {

    void seedCategories() throws IOException;

    void seedUsers() throws IOException;

    void seedProducts() throws IOException;

    default void seedAllData() throws IOException {
        this.seedCategories();
        this.seedUsers();
        this.seedProducts();
    }
}
