package softuni.expirationManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.recipe.RecipeBriefDTO;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.service.RecipeService;
import softuni.expirationManager.service.UserService;

import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;

    @Autowired
    public UserController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

        UserProfileDTO userProfileDTO = this.userService.getUserInfoById(userDetails.getId());

        model.addAttribute("userProfileDTO", userProfileDTO);
        model.addAttribute("isAdmin", isPrincipalAdmin(userDetails));
        model.addAttribute("seeSubscribe", true);

        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@AuthenticationPrincipal MyUserDetails userDetails,
                             @PathVariable("id") Long userId, Model model) {

        if (Objects.equals(userId, userDetails.getId())) {
            return "redirect:/profile";
        }

        UserProfileDTO userProfileDTO = this.userService.getUserInfoById(userId);
        boolean isAdmin = this.userService.isAdmin(userId);

        model.addAttribute("userProfileDTO", userProfileDTO);
        model.addAttribute("isAdmin", this.userService.isAdmin(userId));
        model.addAttribute("seeSubscribe", isPrincipalAdmin(userDetails));

        return "profile";
    }

    @GetMapping("/profile/{id}/recipes")
    public String getProfile(@PathVariable("id") Long userId, Model model,
                             @PageableDefault(size = 3, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<RecipeBriefDTO> recipesByAuthor = this.recipeService.getRecipesByAuthor(userId, pageable);
        String authorUsername = this.userService.getUsernameById(userId);

        model.addAttribute("username", authorUsername);
        model.addAttribute("recipes", recipesByAuthor);

        return "recipes-by";
    }

    @PostMapping("/profile/{id}/subscription")
    public String switchSubscription(@PathVariable("id") Long userId) {

        this.userService.switchSubscription(userId);

        return "redirect:/profile/" + userId;
    }

    @PostMapping("/admin/switch/{id}")
    public String addRole(@PathVariable("id") Long userId) {

        this.userService.switchAdminRole(userId);

        return "redirect:/profile/" + userId;
    }

    private boolean isPrincipalAdmin(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
