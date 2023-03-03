package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserLoginDTO;
import bg.softuni.mobilelele.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
    @ModelAttribute("userLoginDTO")
    public UserLoginDTO initUserModel() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("auth-login");
    }

//    @PostMapping("/login")
//    public ModelAndView postLogin(@Valid UserLoginDTO userLoginDTO,
//                                 BindingResult bindingResult,
//                                 RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
//
//            return super.redirect("/users/login");
//        }
//
//        this.userService.login(userLoginDTO);
//
//        return  super.redirect("/");
//    }

    @PostMapping("/login-error")
    public ModelAndView postLoginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                       RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return super.redirect("/users/login");
    }

//    @PostMapping("/logout")
//    public ModelAndView getLogout() {
////        this.userService.logout();
//        return super.redirect("/");
//    }
}
