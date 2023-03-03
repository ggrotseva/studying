package softuni.expirationManager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/users/login")
    public String getLogin() {
        return "login";
    }
}
