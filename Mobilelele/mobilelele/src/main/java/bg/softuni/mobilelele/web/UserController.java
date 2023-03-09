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

    @ModelAttribute("userLoginDTO")
    public UserLoginDTO initUserModel() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("auth-login");
    }


    @PostMapping("/login-error")
    public ModelAndView postLoginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                       RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return super.redirect("/users/login");
    }

}
