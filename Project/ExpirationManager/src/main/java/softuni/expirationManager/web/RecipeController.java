package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.dtos.RecipeAddDTO;
import softuni.expirationManager.service.RecipeService;

import java.security.Principal;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/add")
    public String getRecipeAdd(@ModelAttribute("recipeAddDTO") RecipeAddDTO recipeAddDTO) {
        return "recipe-add";
    }

    @PostMapping("/recipes/add")
    public String postRecipeAdd(@Valid RecipeAddDTO recipeAddDTO,
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
