package softuni.expirationManager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ApplicationUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepo;

    private ApplicationUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailsService(mockUserRepo);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        UserEntity testUser = new UserEntity("testUser", "Test", "Testov",
                "test@email.test", "topsecret",
                List.of(new UserRoleEntity().setRole(UserRoleEnum.ADMIN), new UserRoleEntity().setRole(UserRoleEnum.USER)),
                true);

        when(mockUserRepo.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser.setId(1L)));

        MyUserDetails userDetails = (MyUserDetails) toTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertEquals(testUser.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.getId(), userDetails.getId());

        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        Iterator<GrantedAuthority> authoritiesIterator = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(), authoritiesIterator.next().getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(), authoritiesIterator.next().getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("NonExisting"));
    }
}
