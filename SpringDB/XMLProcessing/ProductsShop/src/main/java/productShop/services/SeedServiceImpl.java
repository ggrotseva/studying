package productShop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productShop.domain.dtos.category.wrappers.CategoryImportWrapperDTO;
import productShop.domain.dtos.product.ProductImportDTO;
import productShop.domain.dtos.product.wrappers.ProductImportWrapperDTO;
import productShop.domain.dtos.user.wrappers.UserImportWrapperDTO;
import productShop.domain.entities.Category;
import productShop.domain.entities.Product;
import productShop.domain.entities.User;
import productShop.repositories.CategoryRepository;
import productShop.repositories.ProductRepository;
import productShop.repositories.UserRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SeedServiceImpl implements SeedService {

    public static final String CATEGORIES_PATH = "ProductsShop/src/main/resources/dbContent/categories.xml";
    public static final String USERS_PATH = "ProductsShop/src/main/resources/dbContent/users.xml";
    public static final String PRODUCTS_PATH = "ProductsShop/src/main/resources/dbContent/products.xml";

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
    public void seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(CATEGORIES_PATH);

            final JAXBContext context = JAXBContext.newInstance(CategoryImportWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final CategoryImportWrapperDTO wrapperDTO = (CategoryImportWrapperDTO) unmarshaller.unmarshal(fileReader);

            final List<Category> categories = wrapperDTO.getCategories().stream()
                    .map(cat -> mapper.map(cat, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(USERS_PATH);

            final JAXBContext context = JAXBContext.newInstance(UserImportWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final UserImportWrapperDTO wrapperDTO = (UserImportWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<User> users = wrapperDTO.getUsers().stream()
                    .map(user -> mapper.map(user, User.class))
                    .collect(Collectors.toList());

            this.userRepository.saveAllAndFlush(users);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(PRODUCTS_PATH);

            final JAXBContext context = JAXBContext.newInstance(ProductImportWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final ProductImportWrapperDTO wrapperDTO = (ProductImportWrapperDTO) unmarshaller.unmarshal(fileReader);

            final List<Product> products = wrapperDTO.getProducts().stream()
                    .map(product -> mapper.map(product, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .collect(Collectors.toList());

            this.productRepository.saveAllAndFlush(products);
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
