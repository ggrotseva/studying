package productShop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productShop.domain.dtos.category.CategoryImportDTO;
import productShop.domain.dtos.product.ProductImportDTO;
import productShop.domain.dtos.user.UserImportDTO;
import productShop.domain.entities.Category;
import productShop.domain.entities.Product;
import productShop.domain.entities.User;
import productShop.repositories.CategoryRepository;
import productShop.repositories.ProductRepository;
import productShop.repositories.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static productShop.constants.Utils.GSON;

@Service
public class SeedServiceImpl implements SeedService {

    public static final String CATEGORIES_PATH = "ProductsShop/src/main/resources/dbContent/categories.json";
    public static final String USERS_PATH = "ProductsShop/src/main/resources/dbContent/users.json";
    public static final String PRODUCTS_PATH = "ProductsShop/src/main/resources/dbContent/products.json";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;


    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository,
                           ProductRepository productRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {

            final FileReader fileReader = new FileReader(CATEGORIES_PATH);
            final CategoryImportDTO[] categories = GSON.fromJson(fileReader, CategoryImportDTO[].class);

            final List<Category> categoriesList = Arrays.stream(categories)
                    .map(cat -> mapper.map(cat, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categoriesList);
            fileReader.close();
        }
    }

    @Override
    public void seedUsers() throws IOException {
        if (this.userRepository.count() == 0) {

            final FileReader fileReader = new FileReader(USERS_PATH);
            final UserImportDTO[] users = GSON.fromJson(fileReader, UserImportDTO[].class);

            final List<User> usersList = Arrays.stream(users)
                    .map(user -> mapper.map(user, User.class))
                    .collect(Collectors.toList());

            this.userRepository.saveAllAndFlush(usersList);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {

            final FileReader fileReader = new FileReader(PRODUCTS_PATH);
            final ProductImportDTO[] products = GSON.fromJson(fileReader, ProductImportDTO[].class);

            final List<Product> productList = Arrays.stream(products)
                    .map(product -> mapper.map(product, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .collect(Collectors.toList());

            this.productRepository.saveAllAndFlush(productList);
            fileReader.close();
        }
    }

    private User getRandomUser() {
        long userCount = this.userRepository.count();

        Long randomUserId = new Random().nextLong(userCount) + 1;

        return this.userRepository.findById(randomUserId)
                .orElseThrow(NoSuchElementException::new);
    }

    private Product setRandomSeller(Product product) {
        User seller = getRandomUser();
        product.setSeller(seller);

        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(700)) > 0) {

            User buyer = getRandomUser();

            while (buyer.equals(product.getSeller())) {
                buyer = getRandomUser();
            }

            product.setBuyer(buyer);
        }

        return product;
    }

    private Product setRandomCategories(Product product) {
        long categoriesDbCount = this.categoryRepository.count();

        int countOfCategories = new Random().nextInt((int) categoriesDbCount);

        Set<Category> categories = new HashSet<>();

        IntStream.range(0, countOfCategories)
                .forEach(n -> categories.add(
                        this.categoryRepository.getRandomCategory().orElseThrow(NoSuchElementException::new)));

        product.setCategories(categories);

        return product;
    }
}
