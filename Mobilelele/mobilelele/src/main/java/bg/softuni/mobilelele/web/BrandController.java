package bg.softuni.mobilelele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/brands")
public class BrandController {

    @GetMapping("/all")
    public ModelAndView viewAllBrands(ModelAndView mav) {
        mav.setViewName("brands");

        return mav;
    }
}
