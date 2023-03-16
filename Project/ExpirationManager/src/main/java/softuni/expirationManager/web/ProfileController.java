package softuni.expirationManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.service.UserService;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model) {
        UserProfileDTO userProfileDTO = this.userService.getUserInfoByName(principal.getName());

        model.addAttribute("userProfileDTO", userProfileDTO);

        return "profile";
    }
}
