package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.dtos.recipe.RecipeAddDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeBriefDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeEditDTO;
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
        List<RecipeBriefDTO> allRecipes = this.recipeService.getAllRecipeBriefs();

        model.addAttribute("recipes", allRecipes);

        return "recipes";
    }

    @GetMapping("/recipes/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model) {
        RecipeDTO recipe = this.recipeService.getRecipeDtoById(id);

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

    @GetMapping("/recipes/{id}/edit")
    public String getEditRecipe(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("recipeEditDTO")) {
            model.addAttribute("recipeEditDTO", this.recipeService.getRecipeEditDtoById(id));
        }

        return "recipe-edit";
    }

    @PutMapping("/recipes/{id}/edit")
    public String putEditRecipe(@Valid RecipeEditDTO recipeEditDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeEditDTO", recipeEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeEditDTO", bindingResult);

            return "redirect:/recipes/" + recipeEditDTO.getId() + "/edit";
        }

        this.recipeService.editRecipe(recipeEditDTO);

        return "redirect:/recipes/" + recipeEditDTO.getId();
    }

    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        this.recipeService.deleteById(id);

        return "redirect:/recipes";
    }


    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ModelAndView handleFileSizeLimitExceeded() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("errorMessage", "File you uploaded exceeds maximum size");

        return mav;
    }
}
