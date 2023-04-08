package softuni.expirationManager.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.expirationManager.model.entities.RecipeEntity;
import softuni.expirationManager.repository.RecipeRepository;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository mockRecipeRepository;

    @Test
    @WithAnonymousUser
    void testIndexPageShown_WithAnonymousUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username = "anito")
    void testHomePage_WithAuthenticatedUser() throws Exception {

        when(mockRecipeRepository.findByIngredientsDescriptionMatchesRegex("шоколад"))
                .thenReturn(Optional.of(Set.of(new RecipeEntity().setName("брауни").setIngredientsDescription("200гр. шоколад").setId(1L),
                        new RecipeEntity().setName("шоколадови бонбони").setIngredientsDescription("20гр. черен шоколад").setId(2L))));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("expiredProducts", "closeToExpiryProducts", "recipes"));
    }

    @Test
    void testAboutPageShown() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    void testChangeLang_CookieIsSent() throws Exception {
        mockMvc.perform(get("/?lang=en_US"))
                .andExpect(status().isOk())
                .andExpect(cookie().value("lang", "en-US"));
    }
}
