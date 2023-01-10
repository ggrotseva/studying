package productShop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SeedService {

    void seedCategories() throws IOException, JAXBException;

    void seedUsers() throws IOException, JAXBException;

    void seedProducts() throws IOException, JAXBException;

    default void seedAllData() throws IOException, JAXBException {
        this.seedCategories();
        this.seedUsers();
        this.seedProducts();
    }
}
