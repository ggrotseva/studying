package softuni.expirationManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeHomeViewDTO;
import softuni.expirationManager.service.ProductService;
import softuni.expirationManager.service.RecipeService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final RecipeService recipeService;
    private final ProductService productService;
    private final CacheManager cacheManager;

    @Autowired
    public HomeController(RecipeService recipeService, ProductService productService, CacheManager cacheManager) {
        this.recipeService = recipeService;
        this.productService = productService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/")
    public String getIndex(Principal principal, Model model) {
        if (principal == null) {
            return "index";
        }

        Cache expiredProducts1 = this.cacheManager.getCache("expiredProducts");
        Cache closeToExpiryProducts1 = this.cacheManager.getCache("closeToExpiryProducts");

        List<ProductHomeViewDTO> expiredProducts = this.productService.getExpiredProducts(principal.getName());
        List<ProductHomeViewDTO> closeToExpiryProducts = this.productService.getCloseToExpiryProducts(principal.getName());

        List<RecipeHomeViewDTO> recipes = this.recipeService.getRecipeIdeas(expiredProducts, closeToExpiryProducts);

        model.addAttribute("expiredProducts", expiredProducts)
                .addAttribute("closeToExpiryProducts", closeToExpiryProducts)
                .addAttribute("recipes", recipes);

        return "home";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }
}
