package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserLoginDTO;
import bg.softuni.mobilelele.model.dto.UserRegisterDTO;
import bg.softuni.mobilelele.service.UserRoleService;
import bg.softuni.mobilelele.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public UserController(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("auth-login");
    }

    @PostMapping("/login")
    public ModelAndView getLogin(UserLoginDTO userLoginDTO) {

        this.userService.login(userLoginDTO);

//        System.out.println("User is logged: " + userService.login(userLoginDTO));

        return super.redirect("/");
    }

//    @GetMapping("/register")
//    public ModelAndView getRegister(ModelAndView modelAndView) {
//
////        List<UserRoleViewDTO> roles = this.userRoleService.getRoles();
////        modelAndView.addObject("roles", roles);
//
//        return super.view("auth-register", modelAndView);
//    }
//
//    @PostMapping("/register")
//    public ModelAndView postRegister(@Valid UserRegisterDTO userRegisterDTO,
//                                     BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return super.redirect("/users/register");
//        }
//
//        this.userService.registerAndLogin(userRegisterDTO);
//
//        return super.redirect("/");
//    }

    @GetMapping("/logout")
    public ModelAndView getLogout() {
        this.userService.logout();
        return super.redirect("/");
    }
}
