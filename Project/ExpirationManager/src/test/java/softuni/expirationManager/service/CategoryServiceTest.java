package softuni.expirationManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.category.CategoryAddDTO;
import softuni.expirationManager.model.dtos.category.CategoryEditDTO;
import softuni.expirationManager.model.dtos.category.CategoryViewDTO;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.UserRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static softuni.expirationManager.utils.Constants.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ImageService mockImageService;

    @Mock
    private ModelMapper mockMapper;

    @Captor
    private ArgumentCaptor<List<CategoryEntity>> categoryListArgumentCaptor;

    @Captor
    private ArgumentCaptor<CategoryEntity> categoryArgumentCaptor;

    private CategoryService testCategoryService;

    @BeforeEach
    void setUp() {
        testCategoryService = new CategoryService(mockCategoryRepository, mockUserRepository, mockImageService, mockMapper);
    }

    @Test
    void testInitStartCategoriesForUser() throws IOException {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER).setId(2L)), true);

        FileInputStream fis = new FileInputStream(DEFAULT_ICON_PATH);
        byte[] defaultIcon = fis.readAllBytes();
        when(mockImageService.getCategoryDefaultIcon()).thenReturn(defaultIcon);

        Map<String, String> categoriesNamesDescriptions = INITIAL_CATEGORIES;

        testCategoryService.initStartCategoriesForUser(user);

        verify(mockCategoryRepository).saveAllAndFlush(categoryListArgumentCaptor.capture());
        List<CategoryEntity> savedCategories = categoryListArgumentCaptor.getValue();

        assertTrue(savedCategories.stream().allMatch(c -> c.getUser().getUsername().equals(user.getUsername())));
        assertEquals(savedCategories.get(1).getIcon(), defaultIcon);
    }

    @Test
    void testAddCategory() throws IOException {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true);

        FileInputStream fis = new FileInputStream(TEST_ICON_PATH);
        byte[] testIcon = fis.readAllBytes();

        MockMultipartFile multipartFile = new MockMultipartFile("icon", testIcon);

        when(mockImageService.readBytes(multipartFile)).thenReturn(testIcon);
        when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        CategoryAddDTO categoryAddDto = new CategoryAddDTO()
                .setName("pasta")
                .setDescription("different pastas")
                .setIcon(multipartFile);

        testCategoryService.addCategory(categoryAddDto, user.getUsername());

        verify(mockCategoryRepository).saveAndFlush(categoryArgumentCaptor.capture());

        CategoryEntity savedCategory = categoryArgumentCaptor.getValue();

        assertEquals(savedCategory.getUser().getEmail(), user.getEmail());

        assertEquals(savedCategory.getIcon(), testIcon);
        assertEquals(savedCategory.getName(), categoryAddDto.getName());
        assertEquals(savedCategory.getDescription(), categoryAddDto.getDescription());
    }

    @Test
    void testEditCategory_WithMultipartFile() throws IOException {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true);

        FileInputStream fis = new FileInputStream(DEFAULT_ICON_PATH);
        byte[] defaultIcon = fis.readAllBytes();

        CategoryEntity testCategory = new CategoryEntity()
                .setIcon(defaultIcon)
                .setName("test")
                .setDescription("testing category description")
                .setUser(user);

        fis = new FileInputStream(DEFAULT_ICON_PATH);
        byte[] newIcon = fis.readAllBytes();

        MockMultipartFile multipartFile = new MockMultipartFile("icon", newIcon);

        CategoryEditDTO categoryEditDto = new CategoryEditDTO()
                .setId(2L)
                .setName("pasta")
                .setDescription("different pastas")
                .setIcon(multipartFile);

        when(mockImageService.readBytes(multipartFile)).thenReturn(newIcon);
        when(mockCategoryRepository.findById(categoryEditDto.getId())).thenReturn(Optional.of(testCategory));

        testCategoryService.editCategory(categoryEditDto);

        verify(mockCategoryRepository).saveAndFlush(categoryArgumentCaptor.capture());

        CategoryEntity savedCategory = categoryArgumentCaptor.getValue();

        assertEquals(savedCategory.getIcon(), newIcon);
        assertEquals(savedCategory.getId(), testCategory.getId());
        assertEquals(savedCategory.getName(), categoryEditDto.getName());
        assertEquals(savedCategory.getDescription(), categoryEditDto.getDescription());
    }

    @Test
    void testEditCategory_NoMultipartFile() throws IOException {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true);

        FileInputStream fis = new FileInputStream(TEST_ICON_PATH);
        byte[] testIcon = fis.readAllBytes();

        CategoryEntity testCategory = new CategoryEntity()
                .setIcon(testIcon)
                .setName("test")
                .setDescription("testing category description")
                .setUser(user);

        MockMultipartFile multipartFile = new MockMultipartFile("icon", new byte[0]);

        CategoryEditDTO categoryEditDto = new CategoryEditDTO()
                .setId(2L)
                .setName("pasta")
                .setDescription("different pastas")
                .setIcon(multipartFile);

        when(mockCategoryRepository.findById(categoryEditDto.getId())).thenReturn(Optional.of(testCategory));

        testCategoryService.editCategory(categoryEditDto);

        verify(mockCategoryRepository).saveAndFlush(categoryArgumentCaptor.capture());

        CategoryEntity savedCategory = categoryArgumentCaptor.getValue();

        assertEquals(savedCategory.getIcon(), testIcon);
        assertEquals(savedCategory.getId(), testCategory.getId());
        assertEquals(savedCategory.getName(), categoryEditDto.getName());
        assertEquals(savedCategory.getDescription(), categoryEditDto.getDescription());
    }

    @Test
    void testFindAllByUserUsername() throws IOException {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true);

        FileInputStream fis = new FileInputStream(TEST_ICON_PATH);
        byte[] testIcon = fis.readAllBytes();

        CategoryEntity testCategory1 = new CategoryEntity()
                .setId(3L)
                .setIcon(testIcon)
                .setName("pasta")
                .setDescription("pasta category")
                .setUser(user);

        CategoryEntity testCategory2 = new CategoryEntity()
                .setId(2L)
                .setIcon(testIcon)
                .setName("test")
                .setDescription("testing category description")
                .setUser(user);

        List<CategoryEntity> categories = new ArrayList<>();
        categories.add(testCategory1);
        categories.add(testCategory2);

        String encodedIcon = Base64.getEncoder().encodeToString(testCategory1.getIcon());

        CategoryViewDTO categoryEditDto1 = new CategoryViewDTO()
                .setId(testCategory1.getId())
                .setName(testCategory1.getName())
                .setDescription(testCategory1.getDescription())
                .setIcon(encodedIcon)
                .setProductsCount(4);

        CategoryViewDTO categoryEditDto2 = new CategoryViewDTO()
                .setId(testCategory2.getId())
                .setName(testCategory2.getName())
                .setDescription(testCategory2.getDescription())
                .setIcon(encodedIcon)
                .setProductsCount(4);

        when(mockCategoryRepository.findAllByUserUsername(user.getUsername())).thenReturn(Optional.of(categories));
        when(mockMapper.map(testCategory1, CategoryViewDTO.class)).thenReturn(categoryEditDto1);
        when(mockMapper.map(testCategory2, CategoryViewDTO.class)).thenReturn(categoryEditDto2);

        List<CategoryViewDTO> actualCategories = testCategoryService.getCategoryViewDtosByUsername(user.getUsername());

        assertEquals(actualCategories.size(), 2);

        assertEquals(actualCategories.get(0).getProductsCount(), 4);
    }

    @Test
    void testAuthorizeActions_IsAuthor() {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true)
                .setId(2L);

        CategoryEntity testCategory = new CategoryEntity()
                .setId(2L)
                .setName("test")
                .setUser(user);

        MyUserDetails myUserDetails = new MyUserDetails(user.getUsername(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.USER)))
                        .setId(2L);

        when(mockCategoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));

        assertTrue(this.testCategoryService.authorizeActions(myUserDetails, 2L));
    }

    @Test
    void testAuthorizeActions_IsNotAuthor() {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true)
                .setId(4L);

        CategoryEntity testCategory = new CategoryEntity()
                .setId(2L)
                .setName("test")
                .setUser(user);

        MyUserDetails myUserDetails = new MyUserDetails(user.getUsername(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.USER)))
                .setId(2L);

        when(mockCategoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));

        assertFalse(this.testCategoryService.authorizeActions(myUserDetails, 2L));
    }

    @Test
    void testAuthorizeActions_IsAdmin() {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true)
                .setId(4L);

        CategoryEntity testCategory = new CategoryEntity()
                .setId(2L)
                .setName("test")
                .setUser(user);

        MyUserDetails myUserDetails = new MyUserDetails(user.getUsername(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_"  + UserRoleEnum.USER),
                                        (new SimpleGrantedAuthority("ROLE_"  + UserRoleEnum.ADMIN))))
                        .setId(2L);

        when(mockCategoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));

        assertTrue(this.testCategoryService.authorizeActions(myUserDetails, 2L));
    }
}
