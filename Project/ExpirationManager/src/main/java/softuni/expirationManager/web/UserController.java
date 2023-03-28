package softuni.expirationManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.service.UserService;

import java.security.Principal;

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

        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        UserProfileDTO userProfileDTO = this.userService.getUserInfoById(id);

        model.addAttribute("userProfileDTO", userProfileDTO);

        return "profile";
    }
}
