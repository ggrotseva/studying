package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.UserDetailsDTO;
import bg.softuni.pathfinder.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView getProfile(ModelAndView modelAndView) {
        UserDetailsDTO userProfile = this.userService.getUserProfile();

        modelAndView.addObject("userProfile", userProfile);

        return super.view("profile", modelAndView);
    }
}
