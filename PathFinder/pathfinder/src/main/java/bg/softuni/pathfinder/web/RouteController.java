package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.RouteAddDTO;
import bg.softuni.pathfinder.model.dto.RouteBriefDTO;
import bg.softuni.pathfinder.model.dto.RouteDTO;
import bg.softuni.pathfinder.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/routes")
public class RouteController extends BaseController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public ModelAndView getAllRoutes(ModelAndView modelAndView) {

        List<RouteBriefDTO> allRoutes = this.routeService.getAllRoutesBriefs();

        modelAndView.addObject("allRoutes", allRoutes);

        return super.view("routes", modelAndView);
    }

    @GetMapping("/details/{id}")
    public ModelAndView getDetails(@PathVariable Long id, ModelAndView modelAndView) {

        modelAndView.setViewName("route-details");

        RouteDTO routeDTO = this.routeService.findById(id);
        modelAndView.addObject("route", routeDTO);

        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getAddRoute(@ModelAttribute RouteAddDTO routeAddDTO) {

        return super.view("add-route");
    }

    @PostMapping("/add")
    public ModelAndView postAddRoute(@Valid RouteAddDTO routeAddDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("routeAddDTO", routeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.routeAddDTO", bindingResult);

            return super.redirect("/routes/add");
        }

        this.routeService.addRoute(routeAddDTO, principal.getName());

        return super.redirect("/routes/all");
    }

    @GetMapping("/{category}")
    public ModelAndView getRoutesByCategory(@PathVariable("category") String categoryName, ModelAndView modelAndView) {

        List<RouteBriefDTO> routes = this.routeService.findByCategoriesContains(categoryName);

        modelAndView.addObject("routes", routes);
        modelAndView.addObject("categoryName", categoryName);

        return super.view("by-category", modelAndView);
    }
}
