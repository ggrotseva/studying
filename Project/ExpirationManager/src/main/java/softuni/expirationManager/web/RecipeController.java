package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.recipe.RecipeAddDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeBriefDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeDTO;
import softuni.expirationManager.model.dtos.recipe.RecipeEditDTO;
import softuni.expirationManager.service.RecipeService;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String getAllRecipes(Model model, @PageableDefault(size = 3) Pageable pageable) {

        Page<RecipeBriefDTO> allRecipesPage = this.recipeService.getAllRecipeBriefs(pageable);

        model.addAttribute("recipes", allRecipesPage);

        return "recipes";
    }

    @GetMapping("/recipes/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails principal) {
        RecipeDTO recipe = this.recipeService.getRecipeDtoById(id);

        model.addAttribute("recipe", recipe);
        model.addAttribute("isAuthenticated", this.recipeService.isOwnerOrAdmin(principal, id));

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
                                @AuthenticationPrincipal MyUserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);

            return "redirect:/recipes/add";
        }

        this.recipeService.addRecipe(recipeAddDTO, principal.getUsername());

        return "redirect:/recipes";
    }

    @PreAuthorize("@recipeService.isOwnerOrAdmin(#principal, #id)")
    @GetMapping("/recipes/{id}/edit")
    public String getEditRecipe(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails principal) {

        if (!model.containsAttribute("recipeEditDTO")) {
            model.addAttribute("recipeEditDTO", this.recipeService.getRecipeEditDtoById(id));
        }

        return "recipe-edit";
    }

    @PreAuthorize("@recipeService.isOwnerOrAdmin(#principal, #recipeEditDTO.getId())")
    @PutMapping("/recipes/{id}/edit")
    public String putEditRecipe(@Valid RecipeEditDTO recipeEditDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal MyUserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeEditDTO", recipeEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeEditDTO", bindingResult);

            return "redirect:/recipes/" + recipeEditDTO.getId() + "/edit";
        }

        this.recipeService.editRecipe(recipeEditDTO);

        return "redirect:/recipes/" + recipeEditDTO.getId();
    }

    @PreAuthorize("@recipeService.isOwnerOrAdmin(#principal, #id)")
    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails principal) {

        this.recipeService.deleteById(id);

        return "redirect:/recipes";
    }


    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ModelAndView handleFileSizeLimitExceeded() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("errorMessage", Constants.REQUEST_SIZE_EXCEEDED);

        return mav;
    }
}
