package softuni.expirationManager.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;
import softuni.expirationManager.model.dtos.user.UserRegisterDTO;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRepository;
import softuni.expirationManager.repository.UserRoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    void testRegisterPageShown() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testLoginPageShown() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testPostLoginErrorPageShown() throws Exception {
        mockMvc.perform(post("/users/login-error")
                        .flashAttr(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "badName")
                        .flashAttr("badCredentials", true)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"))
                .andExpect(flash().attribute("badCredentials", true))
                .andExpect(flash().attribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "badName"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .param("username", "abcdef")
                        .param("email", "test.abcdef@email.email")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .param("isSubscribed", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    void testUserRegistration_PasswordConfirmFails() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .param("username", "abcdef")
                        .param("email", "test.abcdef@email.email")
                        .param("password", "topsecret")
                        .param("confirmPassword", "notMatching")
                        .param("isSubscribed", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));
    }

    @Test
    void testUserRegistration_UserExists() throws Exception {

        MvcResult result = mockMvc.perform(post("/users/register")
                        .param("username", "peshkata")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .param("email", "pesho.petrov@email.email")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .param("isSubscribed", "true")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(302))
                .andReturn();

        BindingResult bindingResult = (BindingResult) result.getFlashMap().get("org.springframework.validation.BindingResult.userRegisterDTO");

        assertEquals(2, bindingResult.getErrorCount());

        assertTrue(bindingResult.hasFieldErrors("username"));
        assertTrue(bindingResult.hasFieldErrors("email"));
    }
}
