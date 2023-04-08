package softuni.expirationManager.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;
import softuni.expirationManager.model.dtos.recipe.RecipeBriefDTO;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testGetProfile_ReturnOwnProfile() throws Exception {
        mockMvc.perform(get("/profile/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("userProfileDTO", "seeSubscribe"));
    }

    @Test
    @WithUserDetails(value = "peshkata", userDetailsServiceBeanName = "userDetailsService")
    void testGetProfile_ViewUserProfile() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/profile/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("userProfileDTO", "seeSubscribe"))
                .andReturn();

        assertNotNull(mvcResult.getModelAndView().getModel());

        Map<String, Object> modelMap = mvcResult.getModelAndView().getModel();

        assertEquals(4, modelMap.size());

        assertInstanceOf(Boolean.class, modelMap.get("seeSubscribe"));
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testGetProfile_ByAdmin_ViewUserProfile() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/profile/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("userProfileDTO", "isAdmin", "seeSubscribe"))
                .andReturn();

        assertNotNull(mvcResult.getModelAndView().getModel());

        Map<String, Object> modelMap = mvcResult.getModelAndView().getModel();

        assertEquals(4, modelMap.size());
        assertInstanceOf(Boolean.class, modelMap.get("isAdmin"));
        assertInstanceOf(Boolean.class, modelMap.get("seeSubscribe"));
    }

    @Test
    @WithMockUser
    void testRecipesBy() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/profile/2/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes-by"))
                .andExpect(model().attributeExists("recipes", "username"))
                .andReturn();

        assertDoesNotThrow(() -> mvcResult.getModelAndView().getModelMap());

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        assertEquals("anito", modelMap.get("username"));

        assertInstanceOf(Page.class, modelMap.get("recipes"));

        Page<RecipeBriefDTO> recipes = (Page<RecipeBriefDTO>) modelMap.get("recipes");

        assertEquals(3, recipes.getTotalElements());
    }

    @Test
    @WithMockUser
    void testRecipesBy_NoSuchUser() throws Exception {
        mockMvc.perform(get("/profile/30/recipes"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithUserDetails(value = "peshkata", userDetailsServiceBeanName = "userDetailsService")
    void testChangeSubscription_ByOwner() throws Exception {
        mockMvc.perform(post("/profile/3/subscription")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testChangeSubscription_ByAdmin() throws Exception {
        mockMvc.perform(post("/profile/3/subscription")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testChangeSubscription_NotByOwner() throws Exception {
        mockMvc.perform(post("/profile/3/subscription")
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testChangeAdmin_ByAdmin() throws Exception {
        mockMvc.perform(post("/admin/switch/3")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/3"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testChangeAdmin_NotByAdmin() throws Exception {
        mockMvc.perform(post("/admin/switch/3")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
