package softuni.expirationManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.service.UserService;

import java.security.Principal;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

        UserProfileDTO userProfileDTO = this.userService.getUserInfoById(userDetails.getId());

        model.addAttribute("userProfileDTO", userProfileDTO);
        model.addAttribute("isAdmin", userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id") Long userId,
                             Model model,
                             @AuthenticationPrincipal MyUserDetails userDetails) {

        if (Objects.equals(userId, userDetails.getId())) {
            return "redirect:/profile";
        }

        UserProfileDTO userProfileDTO = this.userService.getUserInfoById(userId);

        model.addAttribute("userProfileDTO", userProfileDTO);
        model.addAttribute("isAdmin", this.userService.isAdmin(userId));

        return "profile";
    }

    @PostMapping("/admin/switch/{id}")
    public String addRole(@PathVariable("id") Long userId) {

        this.userService.switchAdminRole(userId);

        return "redirect:/profile/" + userId;
    }

}
