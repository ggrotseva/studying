package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.expirationManager.model.MyUserDetails;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.expirationManager.utils.Constants.*;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public boolean authorizeActions(MyUserDetails userDetails, Long categoryId) {
        Long categoryUserId = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(NO_CATEGORY_FOUND))
                .getUser().getId();

        return userDetails.getId().equals(categoryUserId)
                || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public void initStartCategoriesForUser(UserEntity userEntity) {
        byte[] defaultIcon = null;

        try {
            FileInputStream fis = new FileInputStream(DEFAULT_ICON_PATH);
            defaultIcon = fis.readAllBytes();
        } catch (IOException e) {
            LOGGER.info("An error occurred while reading from path: " + DEFAULT_ICON_PATH);
        }

        final byte[] finalDefaultIcon = defaultIcon;

        List<CategoryEntity> categories = INITIAL_CATEGORIES.entrySet().stream().map(e ->
                        new CategoryEntity()
                                .setName(e.getKey())
                                .setDescription(e.getValue())
                                .setIcon(finalDefaultIcon)
                                .setUser(userEntity))
                .collect(Collectors.toList());

        this.categoryRepository.saveAllAndFlush(categories);
    }

    public void addCategory(CategoryAddDTO categoryAddDTO, String username) {
        UserEntity principalUser = this.userRepository.findByUsername(username).orElseThrow();

        CategoryEntity newCategory = new CategoryEntity().setName(categoryAddDTO.getName())
                .setDescription(categoryAddDTO.getDescription())
                .setUser(principalUser);

        try {
            newCategory.setIcon(categoryAddDTO.getIcon().getBytes());
        } catch (IOException e) {
            newCategory.setIcon(null);
            LOGGER.info("An error occurred while reading from html form of file: " + categoryAddDTO.getIcon().getOriginalFilename());
        }

        this.categoryRepository.saveAndFlush(newCategory);
    }

    public void editCategory(CategoryEditDTO categoryEditDTO) {
        CategoryEntity category = this.categoryRepository.findById(categoryEditDTO.getId())
                .orElseThrow(() -> new NoSuchElementException(NO_CATEGORY_FOUND));

        category.setName(categoryEditDTO.getName())
                .setDescription(categoryEditDTO.getDescription());

        if (!categoryEditDTO.getIcon().isEmpty()) {
            try {
                category.setIcon(categoryEditDTO.getIcon().getBytes());
            } catch (IOException e) {
                category.setIcon(null);
                LOGGER.info("An error occurred while reading from html form of file: " + categoryEditDTO.getIcon().getOriginalFilename());
            }
        }

        this.categoryRepository.saveAndFlush(category);
    }

    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Transactional
    public List<CategoryViewDTO> findAllByUserUsername(String name) {
        return this.categoryRepository.findAllByUserUsername(name)
                .orElseThrow(() -> new NoSuchElementException(NO_CATEGORY_FOUND))
                .stream()
                .map(c -> this.mapper.map(c, CategoryViewDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryNameIdDTO getCategoryNameIdDto(Long id) {
        CategoryEntity category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_CATEGORY_FOUND));

        return this.mapper.map(category, CategoryNameIdDTO.class);
    }

    public CategoryEditDTO getCategoryEditDtoById(Long id) {
        return this.mapper.map(this.categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(NO_CATEGORY_FOUND)), CategoryEditDTO.class);
    }

}
