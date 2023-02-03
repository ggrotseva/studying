package bg.softuni.mobilelele.demo;

import bg.softuni.mobilelele.web.BaseController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/demo")
public class StateController extends BaseController {

    private static final String USERNAME_KEY = "uname";

//---------------------------------SESSION--------------------------------------------------------------------------

    @GetMapping("/register")
    public ModelAndView getRegisterWithSession(HttpSession session) {
        return super.view("demo/register");
    }

    @PostMapping("/register")
    public ModelAndView getRegisterWithSession(HttpSession session,
                                               @RequestParam String username) {
        session.setAttribute(USERNAME_KEY, username);
        return super.redirect("/demo/login");
    }

    @GetMapping("/login")
    public ModelAndView getLoginWithSession(HttpSession session, Model model) {

        Object unameAttribute = session.getAttribute(USERNAME_KEY);
        model.addAttribute(USERNAME_KEY, unameAttribute != null ? unameAttribute : "");
        return super.view("demo/login");
    }

//----------------------------------COOKIES-------------------------------------------------------------------------

//    @GetMapping("/register")
//    public ModelAndView getRegister(ModelAndView modelAndView,
//                                    @CookieValue(required = false, defaultValue = COOKIE_NAME) String username) {
//        modelAndView.addObject(COOKIE_NAME, username);
//        return super.view("demo/register");
//    }
//
//    @PostMapping("/register")
//    public ModelAndView getRegister(HttpServletResponse response,
//                                    @RequestParam String username) {
//        Cookie cookie = new Cookie(COOKIE_NAME, username);
//        response.addCookie(cookie);
//        return super.redirect("/demo/login");
//    }
//
//    @GetMapping("/login")
//    public ModelAndView getLogin(ModelAndView modelAndView,
//                                 @CookieValue(name = COOKIE_NAME, defaultValue = "")
//                                         String username) {
//        modelAndView.addObject(COOKIE_NAME, username);
//        return super.view("demo/login", modelAndView);
//    }
}
