package carDealer.services.seed;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SeedService {

    void seedSuppliers() throws IOException, JAXBException;

    void seedParts() throws IOException, JAXBException;

    void seedCars() throws IOException, JAXBException;

    void seedCustomers() throws IOException, JAXBException;

    void seedSales();

    default void seedAllData() throws IOException, JAXBException {
        seedSuppliers();
        seedParts();
        seedCars();
        seedCustomers();
        seedSales();
    }
}
