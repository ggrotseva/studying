package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.category.CategoryAddDTO;
import softuni.expirationManager.model.dtos.category.CategoryEditDTO;
import softuni.expirationManager.model.dtos.category.CategoryNameIdDTO;
import softuni.expirationManager.model.dtos.category.CategoryViewDTO;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.UserRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final String DEAFAULT_ICON_PATH = "src/main/resources/static/images/jar-of-jam.png";

    private final static Map<String, String> INITIAL_CATEGORIES = Map.of(
            "grains", "pasta, flours, oats, rice, etc.",
            "jars", "jams, tahini, honey, pickles, etc.",
            "cans", "chickpeas, beans, coconut milk, etc.",
            "sweets", "biscuits, pralines, chocolates, etc.");

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void initStartCategories(UserEntity userEntity) throws IOException {
        FileInputStream fis = new FileInputStream(DEAFAULT_ICON_PATH);
        byte[] iconBytes = fis.readAllBytes();

        List<CategoryEntity> categories = INITIAL_CATEGORIES.entrySet().stream().map(e ->
                        new CategoryEntity()
                                .setName(e.getKey())
                                .setDescription(e.getValue())
                                .setIcon(iconBytes)
                                .setUser(userEntity))
                .collect(Collectors.toList());

        this.categoryRepository.saveAllAndFlush(categories);
    }

    public void addCategory(CategoryAddDTO categoryAddDTO, String username) throws IOException {
        CategoryEntity newCategory = new CategoryEntity().setName(categoryAddDTO.getName())
                .setDescription(categoryAddDTO.getDescription())
                .setIcon(categoryAddDTO.getIcon().getBytes());

        UserEntity principalUser = this.userRepository.findByUsername(username).orElseThrow();

        newCategory.setUser(principalUser);

        this.categoryRepository.saveAndFlush(newCategory);
    }

    public List<CategoryViewDTO> findAllByUserUsername(String name) {
        return this.categoryRepository.findAllByUserUsername(name).orElseThrow()
                .stream().map(c -> this.mapper.map(c, CategoryViewDTO.class))
                .map(c -> {
                    if (c.getIcon() != null || c.getIcon().length == 0) {
                        return c.setIconBase64(Base64.getEncoder().encodeToString(c.getIcon()));
                    }
                    return c;
                })
                .collect(Collectors.toList());
    }

    public CategoryNameIdDTO getCategoryNameIdDTO(Long id) {
        CategoryEntity category = this.categoryRepository.findById(id).orElseThrow();

        return this.mapper.map(category, CategoryNameIdDTO.class);
    }

    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    public CategoryEditDTO getCategoryEditDtoById(Long id) {
        return this.mapper.map(this.categoryRepository.findById(id).orElseThrow(), CategoryEditDTO.class);
    }

    public void editCategory(CategoryEditDTO categoryEditDTO) throws IOException {
        CategoryEntity category = this.categoryRepository.findById(categoryEditDTO.getId()).orElseThrow();

        category.setName(categoryEditDTO.getName())
                .setDescription(categoryEditDTO.getDescription());

        if (!categoryEditDTO.getIcon().isEmpty()) {
            category.setIcon(categoryEditDTO.getIcon().getBytes());
        }

        this.categoryRepository.saveAndFlush(category);
    }
}
