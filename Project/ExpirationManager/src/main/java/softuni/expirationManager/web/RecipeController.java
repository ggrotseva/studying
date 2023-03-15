package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.dtos.RecipeAddDTO;
import softuni.expirationManager.model.dtos.RecipeBriefDTO;
import softuni.expirationManager.model.dtos.RecipeDTO;
import softuni.expirationManager.service.RecipeService;

import java.security.Principal;
import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String getAllRecipes(Model model) {
        List<RecipeBriefDTO> allRecipes = this.recipeService.getAllBriefs();

        model.addAttribute("recipes", allRecipes);

        return "recipes";
    }

    @GetMapping("/recipes/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model) {
        RecipeDTO recipe = this.recipeService.getById(id);

        model.addAttribute("recipe", recipe);

        return "recipe-details";
    }

    @GetMapping("/recipes/add")
    public String getAddRecipe(Model model) {
        if (!model.containsAttribute("recipeAddDTO")) {
            model.addAttribute("recipeAddDTO", new RecipeAddDTO());
        }
        return "recipe-add";
    }

    @PostMapping("/recipes/add")
    public String postAddRecipe(@Valid RecipeAddDTO recipeAddDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);

            return "redirect:/recipes/add";
        }

        this.recipeService.createRecipe(recipeAddDTO, principal.getName());

        return "redirect:/recipes";
    }

}
