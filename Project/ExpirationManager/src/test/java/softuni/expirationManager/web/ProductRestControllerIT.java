package softuni.expirationManager.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @WithUserDetails(value = "nikita", userDetailsServiceBeanName = "userDetailsService")
    void testGetProductsOfCategory_ByAuthor_ReturnsProducts() throws Exception {
        mockMvc.perform(get("/categories/19/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("паста")))
                .andExpect(jsonPath("$.[0].brand", is("Deroni")));
    }

    @Test
    @WithUserDetails(value = "nikita", userDetailsServiceBeanName = "userDetailsService")
    void testGetProductsOfCategory_NotByAuthor_ReturnsForbidden() throws Exception {
        mockMvc.perform(get("/categories/17/products"))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testGetProduct_ByAuthor_ReturnsProduct() throws Exception {
        mockMvc.perform(get("/categories/17/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("шоколад")))
                .andExpect(jsonPath("$.brand", is("Gaillot")))
                .andExpect(jsonPath("$.description", is("черен")))
                .andExpect(jsonPath("$.expiryDate", is("2023-03-25")))
                .andExpect(jsonPath("$.categoryUserId", is(2)));
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testGetProduct_ByAdmin_ReturnsProduct() throws Exception {
        mockMvc.perform(get("/categories/17/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("шоколад")))
                .andExpect(jsonPath("$.brand", is("Gaillot")))
                .andExpect(jsonPath("$.description", is("черен")))
                .andExpect(jsonPath("$.expiryDate", is("2023-03-25")))
                .andExpect(jsonPath("$.categoryUserId", is(2)));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testGetProduct_NotExisting_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/categories/17/products/30"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testPostProduct_ReturnsURI() throws Exception {

        ProductAddDTO testProductAddDTO = new ProductAddDTO().setName("шоколадови капки")
                .setExpiryDate(LocalDate.of(2024, 1, 25));

        mockMvc.perform(post("/categories/17/products")
                        .content(objectMapper.writeValueAsString(testProductAddDTO))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    void testPostProduct_ByAdmin_ReturnsURI() throws Exception {

        ProductAddDTO testProductAddDTO = new ProductAddDTO().setName("шоколадови капки")
                .setBrand("bett'r")
                .setExpiryDate(LocalDate.of(2024, 2, 2));

        mockMvc.perform(post("/categories/17/products")
                        .content(objectMapper.writeValueAsString(testProductAddDTO))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testDeleteProduct_WithAuthor_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/categories/17/products/4")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "anito", userDetailsServiceBeanName = "userDetailsService")
    void testDeleteProduct_NoSuchProduct_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/categories/17/products/30")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}
