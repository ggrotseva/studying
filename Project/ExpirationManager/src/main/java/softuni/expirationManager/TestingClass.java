package softuni.expirationManager;

import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.expirationManager.service.CategoryService;

//@Component
public class TestingClass implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public TestingClass(CategoryRepository categoryRepository, CategoryService categoryService, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
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
