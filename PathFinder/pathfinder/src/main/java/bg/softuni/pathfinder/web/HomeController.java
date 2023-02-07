package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.MostCommentedRouteDTO;
import bg.softuni.pathfinder.service.RouteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String USERNAME_KEY = "uname";
    private final RouteService routeService;

    public HomeController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/")
    public String home(HttpSession httpSession, Model model) {

//        final Object sessionUsername = httpSession.getAttribute(USERNAME_KEY);

        MostCommentedRouteDTO routeDto = routeService.getMostCommented();

        model.addAttribute("mostCommented", routeDto);
//        model.addAttribute(USERNAME_KEY, sessionUsername);

        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
