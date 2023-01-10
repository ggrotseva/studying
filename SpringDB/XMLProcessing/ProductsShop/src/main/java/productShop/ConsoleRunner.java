package productShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import productShop.services.ProductService;
import productShop.services.SeedService;
import productShop.services.UserService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedService.seedAllData();

//        productsInRange();
//        successfullySoldProducts();
//        getCategoryStatistics();
//        getUsersAndProducts();

    }

    // 4
    private void getUsersAndProducts() throws JAXBException {
        this.userService.findAllWithSoldProductsOrderByCount();
    }

    // 3
    private void getCategoryStatistics() throws JAXBException {
        this.productService.getCategoryStatistics();
    }

    // 2
    private void successfullySoldProducts() throws JAXBException {
        this.userService.findAllUsersWithAtLeastOneSoldProduct();
    }

    // 1
    private void productsInRange() throws JAXBException {
        this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(500, 1000);
    }

}
