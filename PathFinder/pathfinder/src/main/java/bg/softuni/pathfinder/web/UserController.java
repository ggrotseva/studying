package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.UserDetailsDTO;
import bg.softuni.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/profile")
    public ModelAndView getProfile(ModelAndView modelAndView, Principal principal) {
        UserDetailsDTO userProfile = mapper.map(this.userService.getUserProfile(principal.getName()),
                UserDetailsDTO.class);

        modelAndView.addObject("userProfile", userProfile);

        return super.view("profile", modelAndView);
    }
}
