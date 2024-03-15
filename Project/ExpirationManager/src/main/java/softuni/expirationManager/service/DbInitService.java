package softuni.expirationManager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.recipe.RecipeFromJsonDTO;
import softuni.expirationManager.model.entities.*;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.*;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.utils.DateTimeProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DbInitService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    private final DateTimeProvider dateTimeProvider;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    private final String defaultPass;
    private final String defaultMailDomain;

    @Autowired
    public DbInitService(UserRoleRepository userRoleRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository,
                         RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository,
                         DateTimeProvider dateTimeProvider,
                         CategoryService categoryService,
                         PasswordEncoder passwordEncoder,
                         ModelMapper mapper,
                         @Value("${expirationManager.default.password}") String defaultPass,
                         @Value("${expirationManager.default.mailDomain}") String defaultMailDomain) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.dateTimeProvider = dateTimeProvider;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.defaultPass = defaultPass;
        this.defaultMailDomain = defaultMailDomain;
    }

    @PostConstruct
    public void initDatabase() throws IOException {
        initUserRoles();
        initUsers();
        initCategories();
        initProducts();
        initRecipes();
    }

    public void initUserRoles() {
        if (this.userRoleRepository.count() == 0) {
            Arrays.stream(UserRoleEnum.values()).forEach(
                    role -> this.userRoleRepository.saveAndFlush(new UserRoleEntity().setRole(role))
            );
        }
    }

    public void initUsers() {
        if (this.userRepository.count() == 0) {
            UserEntity admin = new UserEntity("admin", "Admin", "Adminov",
                    "admin.adminov" + defaultMailDomain,
                    this.passwordEncoder.encode(defaultPass),
                    this.userRoleRepository.findAll(), true);

            this.userRepository.saveAndFlush(admin);

            List<UserEntity> users = new LinkedList<>();

            UserRoleEntity roleUser = this.userRoleRepository.findByRole(UserRoleEnum.USER)
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_ROLE_FOUND));

            users.add(new UserEntity("anito", "Anna", "Atanasova",
                    "anna.atanasova" + defaultMailDomain, this.passwordEncoder.encode(defaultPass),
                    List.of(roleUser), true));

            users.add(new UserEntity("peshkata", "Pesho", "Petrov",
                    "pesho.petrov" + defaultMailDomain, this.passwordEncoder.encode(defaultPass),
                    List.of(roleUser), true));

            users.add(new UserEntity("nikita", "Nikoleta", "Nikolova",
                    "nikita.nikolova" + defaultMailDomain, this.passwordEncoder.encode(defaultPass),
                    List.of(roleUser), true));

            this.userRepository.saveAllAndFlush(users);
        }
    }

    public void initCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            this.userRepository.findAll().forEach(this.categoryService::initStartCategoriesForUser);

            List<CategoryEntity> categories = new LinkedList<>();

            categories.add(new CategoryEntity()
                    .setName("chocolates")
                    .setUser(this.userRepository.findByUsername("anito")
                            .orElseThrow(() -> new NoSuchElementException(Constants.NO_USER_FOUND)))
                    .setIcon(Files.readAllBytes(Path.of("src/main/resources/init/sweets.png"))));

            categories.add(new CategoryEntity()
                    .setName("fridge").setDescription("refrigerated items")
                    .setUser(this.userRepository.findByUsername("peshkata")
                            .orElseThrow(() -> new NoSuchElementException(Constants.NO_USER_FOUND)))
                    .setIcon(Files.readAllBytes(Path.of("src/main/resources/init/fridge.png"))));

            categories.add(new CategoryEntity()
                    .setName("pasta").setDescription("pastas")
                    .setUser(this.userRepository.findByUsername("nikita")
                            .orElseThrow(() -> new NoSuchElementException(Constants.NO_USER_FOUND)))
                    .setIcon(Files.readAllBytes(Path.of("src/main/resources/init/pasta.png"))));

            this.categoryRepository.saveAllAndFlush(categories);
        }
    }

    public void initProducts() {
        if (this.productRepository.count() == 0) {
            CategoryEntity chocolates = this.categoryRepository.findByName("chocolates")
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_CATEGORY_FOUND));
            CategoryEntity fridge = this.categoryRepository.findByName("fridge")
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_CATEGORY_FOUND));
            CategoryEntity pasta = this.categoryRepository.findByName("pasta")
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_CATEGORY_FOUND));

            List<ProductEntity> products = new LinkedList<>();

            products.add(new ProductEntity("шоколад", "Gaillot", "черен", this.dateTimeProvider.getDateToday().minusDays(10), chocolates));
            products.add(new ProductEntity("шоколад", "bett'r", "бял капки", this.dateTimeProvider.getDateToday().plusDays(15), chocolates));
            products.add(new ProductEntity("шоколад", "Gaillot", "бял", this.dateTimeProvider.getDateToday().plusYears(1), chocolates));
            products.add(new ProductEntity("шоколад", "Gaillot", "розов", this.dateTimeProvider.getDateToday().plusMonths(1), chocolates));

            products.add(new ProductEntity("яйца", "p&p", "", this.dateTimeProvider.getDateToday().plusDays(15), fridge));
            products.add(new ProductEntity("масло", "немско", "250гр", this.dateTimeProvider.getDateToday().plusDays(20), fridge));

            products.add(new ProductEntity("паста", "Deroni", "царевично пенне", this.dateTimeProvider.getDateToday().minusMonths(2), pasta));
            products.add(new ProductEntity("спагети", "Barilla", "", this.dateTimeProvider.getDateToday().plusYears(1), pasta));

            this.productRepository.saveAllAndFlush(products);
        }
    }

    public void initRecipes() throws IOException {
        if (this.recipeRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            List<RecipeFromJsonDTO> recipeDtos = Arrays.asList(
                    objectMapper.readValue(new File("src/main/resources/init/recipes.json"),
                    RecipeFromJsonDTO[].class));

            List<RecipeEntity> recipes = recipeDtos.stream()
                    .map(r -> this.mapper.map(r, RecipeEntity.class)
                            .setId(null)
                            .setCreated(LocalDateTime.parse(r.getCreated(), DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss")))
                            .setModified(LocalDateTime.parse(r.getModified(), DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss")))
                            .setAuthor(this.userRepository.findById(r.getAuthorId())
                                .orElseThrow(() -> new NoSuchElementException(Constants.NO_USER_FOUND))))
                    .toList();

            this.recipeRepository.saveAllAndFlush(recipes);
        }
    }
}
