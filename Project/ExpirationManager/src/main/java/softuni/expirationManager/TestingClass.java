package softuni.expirationManager;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import softuni.expirationManager.service.CategoryService;
import softuni.expirationManager.service.ProductService;

import java.util.List;
import java.util.NoSuchElementException;

//@Component
public class TestingClass implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CacheManager cacheManager;

    @Autowired
    public TestingClass(CategoryRepository categoryRepository,
                        CategoryService categoryService,
                        ProductRepository productRepository,
                        ProductService productService,
                        CacheManager cacheManager) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.productService = productService;
        this.cacheManager = cacheManager;
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

//        List<ProductViewDTO> cat10 = this.productService.findAllByCategoryId(30L);
//
//        CategoryEntity categoryEntity = this.categoryRepository.findById(30L)
//                .orElseThrow(() -> new NoSuchElementException(Constants.NO_CATEGORY_FOUND));
    }

    @PostConstruct
    public void clearCache() {
        this.cacheManager.getCache("recipeIdeas").clear();
    }
}
