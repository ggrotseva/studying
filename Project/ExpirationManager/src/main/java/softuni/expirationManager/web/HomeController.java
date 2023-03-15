package softuni.expirationManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.expirationManager.model.dtos.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.RecipeHomeViewDTO;
import softuni.expirationManager.service.ProductService;
import softuni.expirationManager.service.RecipeService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final RecipeService recipeService;
    private final ProductService productService;

    public HomeController(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getIndex(Principal principal, Model model) {
        if (principal == null) {
            return "index";
        }

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
