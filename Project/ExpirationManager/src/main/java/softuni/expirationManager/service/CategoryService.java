package softuni.expirationManager.service;

import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.CategoryAddDTO;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.UserRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final static Map<String, String> INITIAL_CATEGORIES = Map.of(
            "grains", "pasta, flours, oats, rice, etc.",
            "jars", "jams, tahini, honey, pickles, etc.",
            "cans", "chickpeas, beans, coconut milk, etc.",
            "sweets", "biscuits, pralines, chocolates, etc.");

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    public void initStartCategories(UserEntity userEntity) throws IOException {
            FileInputStream fis = new FileInputStream("src/main/resources/static/images/jar-of-jam.png");
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
}
