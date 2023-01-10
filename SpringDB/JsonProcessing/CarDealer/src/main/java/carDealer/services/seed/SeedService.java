package carDealer.services.seed;

import java.io.IOException;

public interface SeedService {

    void seedSuppliers() throws IOException;

    void seedParts() throws IOException;

    void seedCars() throws IOException;

    void seedCustomers() throws IOException;

    void seedSales();

    default void seedAllData() throws IOException {
        seedSuppliers();
        seedParts();
        seedCars();
        seedCustomers();
        seedSales();
    }
}
