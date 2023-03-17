package softuni.expirationManager;

import jakarta.transaction.Transactional;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

//@Component
public class TestingClass implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public TestingClass(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

//    @Transactional
    @Override
    public void run(String... args) throws Exception {

//        CategoryEntity category = this.categoryRepository.findById(3L).orElseThrow();
//
//        ProductEntity product = new ProductEntity().setName("шоколадови капки")
//                .setBrand("bett'r").setDescription("ruby")
//                .setExpiryDate(LocalDate.of(2025, 2, 13))
//                .setCategory(category);
//
//        this.productRepository.save(product);
    }
}
