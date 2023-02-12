package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserLoginDTO;
import bg.softuni.mobilelele.service.UserService;
import jakarta.validation.Valid;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userLoginDTO")
    public UserLoginDTO initUserModel() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("auth-login");
    }

    @PostMapping("/login")
    public ModelAndView getLogin(@Valid UserLoginDTO userLoginDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.userService.login(userLoginDTO)) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);

            // when I put an error message in the form, I'll need this
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);

            return super.redirect("/users/login");
        }

        return  super.redirect("/");
    }

    @GetMapping("/logout")
    public ModelAndView getLogout() {
        this.userService.logout();
        return super.redirect("/");
    }
}
