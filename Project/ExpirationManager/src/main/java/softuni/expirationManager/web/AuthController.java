package softuni.expirationManager.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.dtos.UserLoginDTO;

@Controller
public class AuthController {

    @ModelAttribute("userLoginDTO")
    public UserLoginDTO initUserLoginDTO() {
        return new UserLoginDTO();
    }

    @GetMapping("/users/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login-error")
    public String postLoginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                       RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }
}
