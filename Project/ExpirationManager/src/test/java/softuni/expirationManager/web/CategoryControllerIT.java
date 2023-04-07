package softuni.expirationManager.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("anita")
    void testCategories_PageShown() throws Exception {

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @WithMockUser
    void testAddCategory_PageShown() throws Exception {

        mockMvc.perform(get("/categories/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-add"))
                .andExpect(model().attributeExists("categoryAddDTO"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testAddCategory_ValidCategory() throws Exception {

        mockMvc.perform(post("/categories/add")
                        .param("name", "rice")
                        .param("description", "Rice bags")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testAddCategory_NotValidCategory() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/categories/add")
                        .param("description", "Rice bags")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        BindingResult bindingResult = (BindingResult) mvcResult.getFlashMap().get("org.springframework.validation.BindingResult.categoryAddDTO");

        assertEquals(1, bindingResult.getErrorCount());

        assertTrue(bindingResult.hasFieldErrors("name"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditCategory_ByAuthor_PageShown() throws Exception {

        mockMvc.perform(get("/categories/5/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-edit"))
                .andExpect(model().attributeExists("categoryEditDTO"));
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testEditCategory_ByAdmin_PageShown() throws Exception {

        mockMvc.perform(get("/categories/6/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-edit"))
                .andExpect(model().attributeExists("categoryEditDTO"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditCategory_NotByAuthor_ErrorPageShown() throws Exception {

        mockMvc.perform(get("/categories/12/edit"))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditCategory_ValidCategory() throws Exception {

        mockMvc.perform(put("/categories/5/edit")
                        .param("name", "new Name")
                        .param("description", "Rice bags")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditCategory_NotValidCategory() throws Exception {

        MvcResult mvcResult = mockMvc.perform(put("/categories/5/edit")
                        .param("description", "Rice bags")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        BindingResult bindingResult = (BindingResult) mvcResult.getFlashMap().get("org.springframework.validation.BindingResult.categoryEditDTO");

        assertEquals(1, bindingResult.getErrorCount());

        assertTrue(bindingResult.hasFieldErrors("name"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testDeleteCategory_ByAuthor() throws Exception {

        mockMvc.perform(delete("/categories/5")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testDeleteCategory_NotByAuthor() throws Exception {

        mockMvc.perform(delete("/categories/12")
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testCategoryPageShown() throws Exception {

        mockMvc.perform(get("/categories/7"))
                .andExpect(status().isOk())
                .andExpect(view().name("category"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testCategoryNotFound() throws Exception {

        mockMvc.perform(get("/categories/30"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}
