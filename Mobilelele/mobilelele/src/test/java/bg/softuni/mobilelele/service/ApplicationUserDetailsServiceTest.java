package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.entities.UserEntity;
import bg.softuni.mobilelele.model.entities.UserRole;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {

    private final String NOT_EXISTING_EMAIL = "pesho@example.com";

    private ApplicationUserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound() {
        // ARRANGE
        UserRole testAdminRole = new UserRole().setRole(UserRoleEnum.ADMIN);
        UserRole testUserRole = new UserRole().setRole(UserRoleEnum.USER);

        String EXISTING_EMAIL = "admin@example.com";

        UserEntity testUserEntity = new UserEntity()
                .setUsername(EXISTING_EMAIL)
                .setPassword("topseret")
                .setUserRoles(List.of(testUserRole, testAdminRole));

        when(mockUserRepository.findByUsername(EXISTING_EMAIL))
                .thenReturn(Optional.of(testUserEntity));
        // EO: ARRANGE

        // ACT
        UserDetails adminDetails = toTest.loadUserByUsername(EXISTING_EMAIL);
        // EO: ACT

        // ASSERT
        Assertions.assertNotNull(adminDetails);

        Assertions.assertEquals(EXISTING_EMAIL, adminDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), adminDetails.getPassword());

        Assertions.assertEquals(2, testUserEntity.getUserRoles().size());
        assertRole(adminDetails.getAuthorities(), "ROLE_ADMIN");
        assertRole(adminDetails.getAuthorities(), "ROLE_USER");
        // EO: ASSERT
    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities,
                            String role) {
        authorities.
                stream().
                filter(a -> role.equals(a.getAuthority())).
                findAny().
                orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTING_EMAIL));
    }
}
