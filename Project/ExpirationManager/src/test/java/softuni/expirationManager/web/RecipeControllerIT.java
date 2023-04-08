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
import org.springframework.validation.BindingResult;
import softuni.expirationManager.model.dtos.recipe.RecipeBriefDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeSearchDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RecipeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testGetAllRecipes_ReturnsAllRecipes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes"))
                .andExpect(model().attributeExists("recipeSearchDTO"))
                .andReturn();

        assertDoesNotThrow(() -> mvcResult.getModelAndView().getModelMap());

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        assertInstanceOf(Page.class, modelMap.get("recipes"));
        assertInstanceOf(RecipeSearchDTO.class, modelMap.get("recipeSearchDTO"));
    }

    @Test
    @WithMockUser
    void testSearchRecipes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/recipes/search")
                        .param("search", "какао"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes"))
                .andExpect(model().attributeExists("recipeSearchDTO"))
                .andReturn();

        assertDoesNotThrow(() -> mvcResult.getModelAndView().getModelMap());

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        assertInstanceOf(Page.class, modelMap.get("recipes"));

        Page<RecipeBriefDTO> recipes = (Page<RecipeBriefDTO>) modelMap.get("recipes");

        assertEquals(2, recipes.getTotalElements());
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testGetRecipeDetails_ByAuthor() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/recipes/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-details"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("isAuthorized"))
                .andReturn();

        assertDoesNotThrow(() -> mvcResult.getModelAndView().getModelMap());

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        assertInstanceOf(RecipeDTO.class, modelMap.get("recipe"));
        assertTrue((boolean) modelMap.get("isAuthorized"));
    }

    @Test
    @WithUserDetails(value = "peshkata", userDetailsServiceBeanName = "userDetailsService")
    void testGetRecipeDetails_NotByAuthor() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/recipes/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-details"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("isAuthorized"))
                .andReturn();

        assertDoesNotThrow(() -> mvcResult.getModelAndView().getModelMap());

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        assertInstanceOf(RecipeDTO.class, modelMap.get("recipe"));
        assertFalse((boolean) modelMap.get("isAuthorized"));
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testGetRecipeDetails_ByAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/recipes/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-details"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("isAuthorized"))
                .andReturn();

        assertDoesNotThrow(() -> mvcResult.getModelAndView().getModelMap());

        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

        assertInstanceOf(RecipeDTO.class, modelMap.get("recipe"));
        assertTrue((boolean) modelMap.get("isAuthorized"));
    }

    @Test
    @WithMockUser
    void testAddRecipe_PageShown() throws Exception {
        mockMvc.perform(get("/recipes/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-add"))
                .andExpect(model().attributeExists("recipeAddDTO"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testAddRecipe_ValidRecipe() throws Exception {
        mockMvc.perform(post("/recipes/add")
                        .param("name", "recipeName")
                        .param("type", "SWEET")
                        .param("ingredientsDescription", "Some ingredients")
                        .param("preparation", "description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testAddRecipe_NotValidRecipe() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/recipes/add")
                        .param("name", "recipeName")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes/add"))
                .andReturn();

        BindingResult bindingResult = (BindingResult) mvcResult.getFlashMap().get("org.springframework.validation.BindingResult.recipeAddDTO");

        assertEquals(3, bindingResult.getErrorCount());

        assertTrue(bindingResult.hasFieldErrors("type"));
        assertTrue(bindingResult.hasFieldErrors("ingredientsDescription"));
        assertTrue(bindingResult.hasFieldErrors("preparation"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditRecipe_PageShown() throws Exception {
        mockMvc.perform(get("/recipes/7/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-edit"))
                .andExpect(model().attributeExists("recipeEditDTO"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditRecipe_ValidRecipe() throws Exception {
        mockMvc.perform(put("/recipes/7/edit")
                        .param("name", "recipeName")
                        .param("type", "SWEET")
                        .param("ingredientsDescription", "Some ingredients")
                        .param("preparation", "description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes/7"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testEditRecipe_NotValidRecipe() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/recipes/7/edit")
                        .param("type", "SWEET")
                        .param("ingredientsDescription", "Some ingredients")
                        .param("preparation", "description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes/7/edit"))
                .andReturn();

        BindingResult bindingResult = (BindingResult) mvcResult.getFlashMap().get("org.springframework.validation.BindingResult.recipeEditDTO");

        assertEquals(1, bindingResult.getErrorCount());

        assertTrue(bindingResult.hasFieldErrors("name"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testDeleteRecipe_ByAuthor() throws Exception {
        mockMvc.perform(delete("/recipes/4")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testDeleteRecipe_NotByAuthor() throws Exception {
        mockMvc.perform(delete("/recipes/1")
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}
