package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
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

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;
import static softuni.expirationManager.utils.Constants.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ModelMapper mapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           ImageService imageService,
                           ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
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
        byte[] defaultIcon = imageService.getCategoryDefaultIcon();

        List<CategoryEntity> categories = INITIAL_CATEGORIES.entrySet().stream().map(e ->
                        new CategoryEntity()
                                .setName(e.getKey())
                                .setDescription(e.getValue())
                                .setIcon(defaultIcon)
                                .setUser(userEntity))
                .collect(Collectors.toList());

        this.categoryRepository.saveAllAndFlush(categories);
    }

    public void addCategory(CategoryAddDTO categoryAddDTO, String username) {
        UserEntity principalUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(NO_USER_FOUND));

        CategoryEntity newCategory = new CategoryEntity()
                .setName(categoryAddDTO.getName())
                .setDescription(categoryAddDTO.getDescription())
                .setUser(principalUser)
                .setIcon(imageService.readBytes(categoryAddDTO.getIcon()));

        this.categoryRepository.saveAndFlush(newCategory);
    }

    public void editCategory(CategoryEditDTO categoryEditDTO) {
        CategoryEntity category = this.categoryRepository.findById(categoryEditDTO.getId())
                .orElseThrow(() -> new NoSuchElementException(NO_CATEGORY_FOUND));

        category.setName(categoryEditDTO.getName())
                .setDescription(categoryEditDTO.getDescription());

        if (!categoryEditDTO.getIcon().isEmpty()) {
            category.setIcon(imageService.readBytes(categoryEditDTO.getIcon()));
        }

        this.categoryRepository.saveAndFlush(category);
    }

    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Transactional
    public List<CategoryViewDTO> getCategoryViewDtosByUsername(String name) {
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
