package softuni.expirationManager;

import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestingClass implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public TestingClass(ProductRepository productRepository,
                        CategoryRepository categoryRepository,
                        PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // testing fetching through UserId and CategoryId

        CategoryEntity category = this.categoryRepository.findById(3L).orElseThrow();

        ProductEntity product = new ProductEntity().setName("шоколадови капки")
                .setExpiryDate(LocalDate.of(2024, 8, 20))
                .setCategory(category);


    }
}
