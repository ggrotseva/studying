package softuni.expirationManager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import softuni.expirationManager.model.CategoryEntity;
import softuni.expirationManager.model.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;

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

//        Scanner scan = new Scanner(System.in);
//
//        Long id = Long.parseLong(scan.nextLine());
//        List<ProductEntity> list = this.productRepository.findByExpiryDateBeforeAndCategoryUserId(LocalDate.now(), id).get();
//
//        for (ProductEntity product : list) {
//            System.out.println(product.getName() + product.getBrand());
//        }

        List<CategoryEntity> categories = this.categoryRepository.findByUserId(1L).get();

        System.out.println(categories.get(2).getIcon());
    }
}
