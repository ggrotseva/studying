package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.dtos.MostCommentedRouteDTO;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final RouteService routeService;

    public HomeController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        MostCommentedRouteDTO routeDto = routeService.getMostCommented();

        model.addAttribute("mostCommented", routeDto);

        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
