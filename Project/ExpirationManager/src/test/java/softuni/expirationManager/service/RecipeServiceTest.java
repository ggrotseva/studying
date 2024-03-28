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
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeAddDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeEditDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeHomeViewDTO;
import softuni.expirationManager.model.entities.RecipeEntity;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.RecipeType;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.RecipeRepository;
import softuni.expirationManager.repository.UserRepository;
import softuni.expirationManager.utils.DateTimeProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    private final String TEST_ICON_PATH = "src/main/resources/init/pasta.png";

    private UserEntity TEST_USER;

    @Mock
    private RecipeRepository mockRecipeRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private CloudinaryService mockCloudinaryService;

    @Mock
    private DateTimeProvider mockDateTimeProvider;

    @Mock
    private ModelMapper mockMapper;

    @Captor
    private ArgumentCaptor<RecipeEntity> recipeArgumentCaptor;

    private RecipeService testRecipeService;

    @BeforeEach
    void setUp() {
        this.testRecipeService = new RecipeService(mockRecipeRepository, mockUserRepository, mockCloudinaryService, mockDateTimeProvider, mockMapper);

        TEST_USER = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER).setId(1L)), true)
                .setId(2L);
    }

    @Test
    void testAddRecipe_NoMultipartFile() {

        RecipeAddDTO recipeAddDTO = new RecipeAddDTO()
                .setName("salad")
                .setType(RecipeType.SAVORY);

        RecipeEntity testRecipe = new RecipeEntity()
                .setName("salad");

        when(mockMapper.map(recipeAddDTO, RecipeEntity.class)).thenReturn(testRecipe);
        when(mockUserRepository.findByUsername(TEST_USER.getUsername())).thenReturn(Optional.of(TEST_USER));
        when(mockDateTimeProvider.getDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 4, 12, 0));

        testRecipeService.addRecipe(recipeAddDTO, TEST_USER.getUsername());

        verify(mockRecipeRepository).saveAndFlush(recipeArgumentCaptor.capture());

        RecipeEntity savedRecipe = recipeArgumentCaptor.getValue();

        assertEquals(savedRecipe.getAuthor().getEmail(), TEST_USER.getEmail());
        assertEquals(RecipeType.SAVORY.getDefaultImageUrl(), savedRecipe.getImageUrl());
        assertEquals(testRecipe.getName(), savedRecipe.getName());
    }

    @Test
    void testAddRecipe_WithMultipartFile() throws IOException {

        FileInputStream fis = new FileInputStream(TEST_ICON_PATH);
        byte[] image = fis.readAllBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", image);

        RecipeAddDTO recipeAddDTO = new RecipeAddDTO()
                .setName("salad")
                .setType(RecipeType.SAVORY)
                .setImage(mockMultipartFile);

        RecipeEntity testRecipe = new RecipeEntity()
                .setName("salad");

        String expectedImageUrl = "https://image.bg";

        when(mockMapper.map(recipeAddDTO, RecipeEntity.class)).thenReturn(testRecipe);
        when(mockUserRepository.findByUsername(TEST_USER.getUsername())).thenReturn(Optional.of(TEST_USER));
        when(mockDateTimeProvider.getDateTimeNow()).thenReturn(LocalDateTime.of(2023, 4, 4, 12, 0));
        when(mockCloudinaryService.saveImageToCloudinary(recipeAddDTO.getImage())).thenReturn(expectedImageUrl);

        testRecipeService.addRecipe(recipeAddDTO, TEST_USER.getUsername());

        verify(mockRecipeRepository).saveAndFlush(recipeArgumentCaptor.capture());

        RecipeEntity savedRecipe = recipeArgumentCaptor.getValue();

        assertEquals(savedRecipe.getAuthor().getEmail(), TEST_USER.getEmail());
        assertEquals(testRecipe.getName(), savedRecipe.getName());
        assertEquals(expectedImageUrl, savedRecipe.getImageUrl());
    }

    @Test
    void testEditRecipe_NoMultipartFile() {

        RecipeEditDTO recipeEditDTO = new RecipeEditDTO()
                .setId(2L)
                .setName("salad")
                .setIngredientsDescription("Some ingredients")
                .setPreparation("Preparation")
                .setType(RecipeType.SWEET);

        String imageUrl = "https://image.bg";

        LocalDateTime created = LocalDateTime.of(2020, 2, 2, 7, 30);
        LocalDateTime modified = LocalDateTime.of(2023, 4, 4, 12, 0);

        RecipeEntity testRecipe = new RecipeEntity()
                .setName("some recipe")
                .setImageUrl(imageUrl)
                .setCreated(created)
                .setModified(created)
                .setRecipeType(RecipeType.SAVORY);

        when(mockRecipeRepository.findById(recipeEditDTO.getId())).thenReturn(Optional.of(testRecipe));
        when(mockDateTimeProvider.getDateTimeNow()).thenReturn(modified);

        testRecipeService.editRecipe(recipeEditDTO);

        verify(mockRecipeRepository).saveAndFlush(recipeArgumentCaptor.capture());

        RecipeEntity savedRecipe = recipeArgumentCaptor.getValue();

        assertEquals(imageUrl, savedRecipe.getImageUrl());
        assertEquals(recipeEditDTO.getName(), savedRecipe.getName());
        assertEquals(recipeEditDTO.getIngredientsDescription(), savedRecipe.getIngredientsDescription());
        assertEquals(recipeEditDTO.getPreparation(), savedRecipe.getPreparation());
        assertEquals(recipeEditDTO.getType(), savedRecipe.getRecipeType());
        assertEquals(created, savedRecipe.getCreated());
        assertEquals(modified, savedRecipe.getModified());
    }

    @Test
    void testEditRecipe_ChangedRecipeType_WithMultipartFile() throws IOException {

        LocalDateTime modified = LocalDateTime.of(2023, 4, 4, 12, 0);

        String imageUrl = "https://image.bg";

        FileInputStream fis = new FileInputStream(TEST_ICON_PATH);
        byte[] image = fis.readAllBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", image);

        RecipeEditDTO recipeEditDTO = new RecipeEditDTO()
                .setId(2L)
                .setImage(mockMultipartFile)
                .setName("salad")
                .setType(RecipeType.SWEET);

        RecipeEntity testRecipe = new RecipeEntity()
                .setName("some recipe")
                .setImageUrl(imageUrl)
                .setModified(LocalDateTime.of(2020, 2, 2, 22, 22))
                .setRecipeType(RecipeType.SAVORY);

        when(mockRecipeRepository.findById(recipeEditDTO.getId())).thenReturn(Optional.of(testRecipe));
        when(mockDateTimeProvider.getDateTimeNow()).thenReturn(modified);
        when(mockCloudinaryService.saveImageToCloudinary(recipeEditDTO.getImage())).thenReturn(imageUrl);

        testRecipeService.editRecipe(recipeEditDTO);

        verify(mockRecipeRepository).saveAndFlush(recipeArgumentCaptor.capture());

        RecipeEntity savedRecipe = recipeArgumentCaptor.getValue();

        assertEquals(imageUrl, savedRecipe.getImageUrl());
        assertEquals(recipeEditDTO.getName(), savedRecipe.getName());
        assertEquals(recipeEditDTO.getType(), savedRecipe.getRecipeType());
        assertEquals(modified, savedRecipe.getModified());
    }

    @Test
    void testEditRecipe_WithDefaultImage_AndChangedRecipeType_NoMultipartFile() {

        RecipeEditDTO recipeEditDTO = new RecipeEditDTO()
                .setId(2L)
                .setName("salad")
                .setType(RecipeType.SWEET);

        RecipeEntity testRecipe = new RecipeEntity()
                .setName("some recipe")
                .setImageUrl(RecipeType.SAVORY.getDefaultImageUrl())
                .setModified(LocalDateTime.of(2020, 2, 2, 22, 22))
                .setRecipeType(RecipeType.SAVORY);

        LocalDateTime modified = LocalDateTime.of(2023, 4, 4, 12, 0);
        when(mockDateTimeProvider.getDateTimeNow()).thenReturn(modified);
        when(mockRecipeRepository.findById(recipeEditDTO.getId())).thenReturn(Optional.of(testRecipe));

        testRecipeService.editRecipe(recipeEditDTO);

        verify(mockRecipeRepository).saveAndFlush(recipeArgumentCaptor.capture());

        RecipeEntity savedRecipe = recipeArgumentCaptor.getValue();

        assertEquals(recipeEditDTO.getName(), savedRecipe.getName());
        assertEquals(recipeEditDTO.getType(), savedRecipe.getRecipeType());
        assertEquals(recipeEditDTO.getType().getDefaultImageUrl(), savedRecipe.getImageUrl());
        assertEquals(modified, savedRecipe.getModified());
    }

    @Test
    void testGetRecipeDtoById_NoSuchRecipe() {
        assertThrows(NoSuchElementException.class, () -> this.testRecipeService.getRecipeDtoById(2L));
    }

    @Test
    void testGetRecipeDtoById() {
        RecipeEntity recipe = new RecipeEntity().setId(2L);

        RecipeDTO recipeDto = new RecipeDTO().setId(2L)
                .setIngredientsDescription("20гр какао" + System.lineSeparator() + "4 яйца")
                .setPreparation("Смеси ги" + System.lineSeparator() + "Печи ги");

        when(mockRecipeRepository.findById(2L))
                .thenReturn(Optional.of(recipe));

        when(mockMapper.map(recipe, RecipeDTO.class))
                .thenReturn(recipeDto);

        RecipeDTO actualRecipeDto = testRecipeService.getRecipeDtoById(recipe.getId());

        assertTrue(actualRecipeDto.getIngredientsDescription().contains("<br/>"));
        assertTrue(actualRecipeDto.getPreparation().contains("<br/>"));
    }

    @Test
    void testGetRecipeIdeas_WithProducts_NoRecipes() {
        ProductHomeViewDTO product = new ProductHomeViewDTO().setName("какао");

        List<ProductHomeViewDTO> mockExpiredProducts = new ArrayList<>();
        List<ProductHomeViewDTO> mockCloseToExpiryProducts = new ArrayList<>();

        mockExpiredProducts.add(product);
        mockCloseToExpiryProducts.add(product);

        when(mockRecipeRepository.findFirst25ByOrderByCreatedDesc())
                .thenReturn(Optional.of(new HashSet<>()));

        List<RecipeHomeViewDTO> actualRecipeIdeas = testRecipeService.getRecipeIdeas(mockExpiredProducts, mockCloseToExpiryProducts);

        assertEquals(0, actualRecipeIdeas.size());
    }

    @Test
    void testGetRecipeIdeas_NoExpiredOrCloseToExpiryProducts_ReturnsFirst25() {

        RecipeEntity recipe1 = new RecipeEntity().setId(1L).setName("Recipe1");
        RecipeEntity recipe2 = new RecipeEntity().setId(2L).setName("Recipe2");

        Set<RecipeEntity> mockRecipes = new HashSet<>();
        mockRecipes.add(recipe1);
        mockRecipes.add(recipe2);

        RecipeHomeViewDTO recipeView1 = new RecipeHomeViewDTO().setName("Recipe1");
        RecipeHomeViewDTO recipeView2 = new RecipeHomeViewDTO().setName("Recipe2");

        when(mockRecipeRepository.findFirst25ByOrderByCreatedDesc())
                .thenReturn(Optional.of(mockRecipes));

        when(mockMapper.map(recipe1, RecipeHomeViewDTO.class)).thenReturn(recipeView1);
        when(mockMapper.map(recipe2, RecipeHomeViewDTO.class)).thenReturn(recipeView2);

        List<ProductHomeViewDTO> mockExpiredProducts = new ArrayList<>();
        List<ProductHomeViewDTO> mockCloseToExpiryProducts = new ArrayList<>();
        List<RecipeHomeViewDTO> actualRecipeIdeas = testRecipeService.getRecipeIdeas(mockExpiredProducts, mockCloseToExpiryProducts);

        assertEquals(2, actualRecipeIdeas.size());
        assertEquals(recipeView1.getName(), actualRecipeIdeas.get(0).getName());
        assertEquals(recipeView2.getName(), actualRecipeIdeas.get(1).getName());
    }

    @Test
    void testGetRecipeIdeas_ReturnsRecipesMatchingProducts() {
        ProductHomeViewDTO product1 = new ProductHomeViewDTO().setName("какао");
        ProductHomeViewDTO product2 = new ProductHomeViewDTO().setName("яйца");
        ProductHomeViewDTO productNotIn = new ProductHomeViewDTO().setName("масло");

        List<ProductHomeViewDTO> mockExpiredProducts = new ArrayList<>();
        List<ProductHomeViewDTO> mockCloseToExpiryProducts = new ArrayList<>();

        mockExpiredProducts.add(product1);
        mockExpiredProducts.add(productNotIn);
        mockCloseToExpiryProducts.add(product2);

        RecipeEntity recipe = new RecipeEntity()
                .setId(1L)
                .setName("Recipe1")
                .setIngredientsDescription("20гр какао, 4 яйца");

        Set<RecipeEntity> mockRecipes = new HashSet<>();
        mockRecipes.add(recipe);

        RecipeHomeViewDTO recipeView = new RecipeHomeViewDTO()
                .setId(1L)
                .setName("Recipe1");

        when(mockRecipeRepository.findByIngredientsDescriptionMatchesRegex(any()))
                .thenReturn(Optional.of(mockRecipes));

        when(mockMapper.map(recipe, RecipeHomeViewDTO.class)).thenReturn(recipeView);

        List<RecipeHomeViewDTO> actualRecipeIdeas = testRecipeService.getRecipeIdeas(mockExpiredProducts, mockCloseToExpiryProducts);

        assertEquals(1, actualRecipeIdeas.size());
        assertTrue(actualRecipeIdeas.get(0).getProducts().contains(product1.getName()));
        assertTrue(actualRecipeIdeas.get(0).getProducts().contains(product2.getName()));

        assertFalse(actualRecipeIdeas.get(0).getProducts().contains(productNotIn.getName()));
    }

    @Test
    void testAuthorizeActions_IsAuthor() {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true)
                .setId(2L);

        RecipeEntity testRecipe = new RecipeEntity()
                .setId(2L)
                .setName("test")
                .setAuthor(user);

        MyUserDetails myUserDetails = new MyUserDetails(user.getUsername(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.USER)), 2L);

        when(mockRecipeRepository.findById(2L)).thenReturn(Optional.of(testRecipe));

        assertTrue(this.testRecipeService.authorizeActions(myUserDetails, 2L));
    }

    @Test
    void testAuthorizeActions_IsNotAuthor() {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true)
                .setId(4L);

        RecipeEntity testRecipe = new RecipeEntity()
                .setId(2L)
                .setName("test")
                .setAuthor(user);

        MyUserDetails myUserDetails = new MyUserDetails("SomeUser", "somePassword",
                List.of(new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.USER)), 2L);

        when(mockRecipeRepository.findById(2L)).thenReturn(Optional.of(testRecipe));

        assertFalse(this.testRecipeService.authorizeActions(myUserDetails, 2L));
    }

    @Test
    void testAuthorizeActions_IsAdmin() {
        UserEntity user = new UserEntity("abcdef", "Test", "Testov",
                "test.abcdef@email.email", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)), true)
                .setId(4L);

        RecipeEntity testRecipe = new RecipeEntity()
                .setId(2L)
                .setName("test")
                .setAuthor(user);

        MyUserDetails myUserDetails = new MyUserDetails("SomeUser", "somePassword",
                List.of(new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.USER),
                        (new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.ADMIN))), 2L);

        when(mockRecipeRepository.findById(2L)).thenReturn(Optional.of(testRecipe));

        assertTrue(this.testRecipeService.authorizeActions(myUserDetails, 2L));
    }
}
