package bg.softuni.mobilelele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offers")
public class OfferController extends BaseController {

    @GetMapping("/all")
    public ModelAndView getAllOffers() {
        return super.view("offers");
    }

    @GetMapping("/add")
    public ModelAndView getAddOffer() {
        return super.view("offers-add");
    }


}
