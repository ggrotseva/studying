package bg.softuni.mobilelele.demo;

import bg.softuni.mobilelele.model.user.dto.UserRegisterDemoDTO;
import bg.softuni.mobilelele.web.BaseController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/demo")
//@SessionScope
public class StateController extends BaseController {

    private final String COOKIE_NAME = "uname";

    @GetMapping("/register")
    public ModelAndView getRegister(Model model,
             @CookieValue(required = false, defaultValue = COOKIE_NAME) String username) {

        model.addAttribute(COOKIE_NAME, username);

        return super.view("demo/register");
    }

    @PostMapping("/register")
    public ModelAndView getRegister(HttpServletResponse response,
                                    @RequestParam() String username) {

        response.addCookie(new Cookie(COOKIE_NAME, username));

        return super.redirect("/demo/login");
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return super.view("demo/login");
    }


//    @PostMapping("/login")
//    public ModelAndView getLogin(UserLoginDTO userLoginDTO) {
//        return super.redirect("/");
//    }
}
