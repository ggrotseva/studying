package bg.softuni.pathfinder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/routes")
public class RouteController {

    @GetMapping("/details/{id}")
    public ModelAndView getDetails(ModelAndView modelAndView) {

        modelAndView.setViewName("route-details");
        return modelAndView;
    }
}
