package softuni.expirationManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.model.dtos.user.UserRegisterDTO;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRepository;
import softuni.expirationManager.repository.UserRoleRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final String USER_FIRST_NAME = "Test";
    private final String USER_LAST_NAME = "Testov";
    private final String USER_USERNAME = "abcdef";
    private final String USER_EMAIL = "test.abcdef@email.email";
    private final String USER_PASSWORD = "topsecret";
    private final String ENCODED_PASSWORD = "encodedPass";

    private UserRoleEntity ADMIN_ROLE;
    private UserRoleEntity USER_ROLE;
    private UserEntity TEST_USER;
    private UserEntity TEST_USER_ADMIN;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @Mock
    private CategoryService mockCategoryService;

    @Mock
    private ModelMapper mockMapper;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    private UserService testUserService;

    @BeforeEach
    void setUp() {
        testUserService = new UserService(mockUserRepository, mockUserRoleRepository, mockCategoryService, mockPasswordEncoder, mockMapper);
        ADMIN_ROLE = new UserRoleEntity().setRole(UserRoleEnum.ADMIN).setId(1L);
        USER_ROLE = new UserRoleEntity().setRole(UserRoleEnum.USER).setId(2L);

        ArrayList<UserRoleEntity> allRoles = new ArrayList<>();
        allRoles.add(ADMIN_ROLE);
        allRoles.add(USER_ROLE);

        ArrayList<UserRoleEntity> userRole = new ArrayList<>();
        userRole.add(USER_ROLE);

        TEST_USER = new UserEntity(USER_USERNAME, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_PASSWORD,
                userRole, true);

        TEST_USER_ADMIN = new UserEntity(USER_USERNAME, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_PASSWORD,
                allRoles, false);
    }

    @Test
    void testRegister_FirstUserAdmin() {
        UserRegisterDTO testUserRegisterDTO = new UserRegisterDTO()
                .setFirstName(USER_FIRST_NAME)
                .setLastName(USER_LAST_NAME)
                .setUsername(USER_USERNAME)
                .setEmail(USER_EMAIL)
                .setPassword(USER_PASSWORD)
                .setConfirmPassword(USER_PASSWORD)
                .setSubscribed(false);

        when(mockUserRepository.count()).thenReturn(0L);

        when(mockMapper.map(testUserRegisterDTO, UserEntity.class)).thenReturn(TEST_USER_ADMIN);
        when(mockPasswordEncoder.encode(testUserRegisterDTO.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(mockUserRoleRepository.findAll()).thenReturn(List.of(ADMIN_ROLE, USER_ROLE));

        testUserService.register(testUserRegisterDTO);

        verify(mockUserRepository).saveAndFlush(userEntityArgumentCaptor.capture());

        UserEntity savedUser = userEntityArgumentCaptor.getValue();

        verify(mockCategoryService).initStartCategoriesForUser(savedUser);

        assertEquals(testUserRegisterDTO.getFirstName(), savedUser.getFirstName());
        assertEquals(testUserRegisterDTO.getLastName(), savedUser.getLastName());
        assertEquals(testUserRegisterDTO.getUsername(), savedUser.getUsername());
        assertEquals(testUserRegisterDTO.getEmail(), savedUser.getEmail());
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
        assertFalse(savedUser.isSubscribed());
        assertEquals(2, savedUser.getUserRoles().size());
    }

    @Test
    void testRegister_AfterFirstUser() {
        UserRegisterDTO testUserRegisterDTO = new UserRegisterDTO()
                .setFirstName(USER_FIRST_NAME)
                .setLastName(USER_LAST_NAME)
                .setUsername(USER_USERNAME)
                .setEmail(USER_EMAIL)
                .setPassword(USER_PASSWORD)
                .setConfirmPassword(USER_PASSWORD)
                .setSubscribed(true);

        when(mockUserRepository.count()).thenReturn(1L);

        when(mockMapper.map(testUserRegisterDTO, UserEntity.class)).thenReturn(TEST_USER);
        when(mockPasswordEncoder.encode(testUserRegisterDTO.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(mockUserRoleRepository.findByRole(UserRoleEnum.USER)).thenReturn(Optional.of(USER_ROLE));

        testUserService.register(testUserRegisterDTO);

        verify(mockUserRepository).saveAndFlush(userEntityArgumentCaptor.capture());

        UserEntity savedUser = userEntityArgumentCaptor.getValue();

        assertEquals(testUserRegisterDTO.getFirstName(), savedUser.getFirstName());
        assertEquals(testUserRegisterDTO.getLastName(), savedUser.getLastName());
        assertEquals(testUserRegisterDTO.getUsername(), savedUser.getUsername());
        assertEquals(testUserRegisterDTO.getEmail(), savedUser.getEmail());
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
        assertTrue(savedUser.isSubscribed());

        assertEquals(1, savedUser.getUserRoles().size());
        assertEquals(USER_ROLE.getRole(), savedUser.getUserRoles().get(0).getRole());
    }

    @Test
    void testGetUserProfileDtoById_Existing() {
        UserProfileDTO testUserProfileDto = new UserProfileDTO()
                .setFirstName(USER_FIRST_NAME)
                .setLastName(USER_LAST_NAME)
                .setUsername(USER_USERNAME)
                .setEmail(USER_EMAIL)
                .setSubscribed(true)
                .setId(2L);

        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(TEST_USER));
        when(mockMapper.map(TEST_USER, UserProfileDTO.class)).thenReturn(testUserProfileDto);

        UserProfileDTO actualUserProfileDto = testUserService.getUserProfileDtoById(2L);

        verify(mockUserRepository).findById(2L);

        assertEquals(testUserProfileDto.getFirstName(), actualUserProfileDto.getFirstName());
        assertEquals(testUserProfileDto.getLastName(), actualUserProfileDto.getLastName());
        assertEquals(testUserProfileDto.getUsername(), actualUserProfileDto.getUsername());
        assertEquals(testUserProfileDto.getEmail(), actualUserProfileDto.getEmail());
        assertEquals(testUserProfileDto.getId(), actualUserProfileDto.getId());
        assertTrue(actualUserProfileDto.isSubscribed());
    }

    @Test
    void testGetUserProfileDtoById_NonExisting() {
        assertThrows(NoSuchElementException.class, () -> testUserService.getUserProfileDtoById(2L));
    }

    @Test
    void testGetUsernameById_Existing() {
        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(TEST_USER));

        String actualUsername = testUserService.getUsernameById(2L);

        verify(mockUserRepository).findById(2L);

        assertEquals(actualUsername, USER_USERNAME);
    }

    @Test
    void testGetUsernameById_NonExisting() {
        assertThrows(NoSuchElementException.class, () -> testUserService.getUsernameById(2L));
    }

    @Test
    void testSwitchAdminRole_AddAdmin() {
        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(TEST_USER));
        when(mockUserRoleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.of(ADMIN_ROLE));

        testUserService.switchAdminRole(2L);

        verify(mockUserRepository).saveAndFlush(userEntityArgumentCaptor.capture());

        UserEntity testUser = userEntityArgumentCaptor.getValue();

        assertTrue(testUser.getUserRoles().stream().anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN)));
    }

    @Test
    void testSwitchAdminRole_RemoveAdmin() {
        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(TEST_USER_ADMIN));

        testUserService.switchAdminRole(2L);

        verify(mockUserRepository).saveAndFlush(userEntityArgumentCaptor.capture());

        UserEntity testUser = userEntityArgumentCaptor.getValue();

        assertFalse(testUser.getUserRoles().stream().anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN)));
    }

    @Test
    void testSwitchSubscription_ToFalse() {
        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(TEST_USER));

        testUserService.switchSubscription(2L);

        verify(mockUserRepository).saveAndFlush(userEntityArgumentCaptor.capture());

        UserEntity testUser = userEntityArgumentCaptor.getValue();

        assertFalse(testUser.isSubscribed());
    }

    @Test
    void testSwitchSubscription_ToTrue() {
        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(TEST_USER_ADMIN));

        testUserService.switchSubscription(2L);

        verify(mockUserRepository).saveAndFlush(userEntityArgumentCaptor.capture());

        UserEntity testUser = userEntityArgumentCaptor.getValue();

        assertTrue(testUser.isSubscribed());
    }
}
