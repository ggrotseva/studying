package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import softuni.expirationManager.model.dtos.recipe.*;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.MyUserDetails;
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

        if (!model.containsAttribute("recipeSearchDTO")) {
           model.addAttribute("recipeSearchDTO", new RecipeSearchDTO());
        }

        Page<RecipeBriefDTO> allRecipesPage = this.recipeService.getAllRecipeBriefs(pageable);

        model.addAttribute("recipes", allRecipesPage);

        return "recipes";
    }

    @GetMapping("/recipes/search")
    public String getSearchRecipes(RecipeSearchDTO recipeSearchDTO,
                                   Model model,
                                   @PageableDefault(size = 3, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<RecipeBriefDTO> allRecipesPage = this.recipeService.searchRecipes(recipeSearchDTO, pageable);

        model.addAttribute("recipes", allRecipesPage);

        return "recipes-search";
    }

    @GetMapping("/recipes/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        RecipeDTO recipe = this.recipeService.getRecipeDtoById(id);

        model.addAttribute("recipe", recipe);
        model.addAttribute("isAuthorized", this.recipeService.authorizeActions(userDetails, id));

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
                                @AuthenticationPrincipal MyUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);

            return "redirect:/recipes/add";
        }

        this.recipeService.addRecipe(recipeAddDTO, userDetails.getUsername());

        return "redirect:/recipes";
    }

    @PreAuthorize("@recipeService.authorizeActions(#userDetails, #id)")
    @GetMapping("/recipes/{id}/edit")
    public String getEditRecipe(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

        if (!model.containsAttribute("recipeEditDTO")) {
            model.addAttribute("recipeEditDTO", this.recipeService.getRecipeEditDtoById(id));
        }

        return "recipe-edit";
    }

    @PreAuthorize("@recipeService.authorizeActions(#userDetails, #recipeEditDTO.getId())")
    @PutMapping("/recipes/{id}/edit")
    public String putEditRecipe(@Valid RecipeEditDTO recipeEditDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal MyUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeEditDTO", recipeEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeEditDTO", bindingResult);

            return "redirect:/recipes/" + recipeEditDTO.getId() + "/edit";
        }

        this.recipeService.editRecipe(recipeEditDTO);

        return "redirect:/recipes/" + recipeEditDTO.getId();
    }

    @PreAuthorize("@recipeService.authorizeActions(#userDetails, #id)")
    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails userDetails) {

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
