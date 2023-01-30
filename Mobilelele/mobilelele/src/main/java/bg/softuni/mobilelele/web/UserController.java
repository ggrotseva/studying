package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.user.dto.UserLoginDTO;
import bg.softuni.mobilelele.model.user.dto.UserRegisterDTO;
import bg.softuni.mobilelele.model.userRole.dto.UserRoleViewDTO;
import bg.softuni.mobilelele.service.userRole.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserRoleService userRoleService;

    public UserController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("auth-login");
    }

    @PostMapping("/login")
    public ModelAndView getLogin(UserLoginDTO userLoginDTO) {
        System.out.println(userLoginDTO);
        return super.redirect("/");
    }

    @GetMapping("/register")
    public ModelAndView getRegister(ModelAndView modelAndView) {

        List<UserRoleViewDTO> roles = this.userRoleService.getRoles();
        modelAndView.addObject("roles", roles);

        return super.view("auth-register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute UserRegisterDTO userRegisterDTO) {

        return super.redirect("/users/login");
    }
}
