package softuni.expirationManager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import softuni.expirationManager.repository.ProductRepository;

@Component
public class TestingClass implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public TestingClass(ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
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

    }
}
