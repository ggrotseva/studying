package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.OfferAddDTO;
import bg.softuni.mobilelele.service.BrandService;
import bg.softuni.mobilelele.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OfferController extends BaseController {

    private final OfferService offerService;
    private final BrandService brandService;

    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public ModelAndView getAllOffers() {
        return super.view("offers");
    }

    @GetMapping("/add")
    public ModelAndView getAddOffer(Model model) {
        if (!model.containsAttribute("offerAddDTO")) {
            model.addAttribute("offerAddDTO", new OfferAddDTO());
        }

        model.addAttribute("brands", this.brandService.getAllBrands());

        return super.view("offer-add");
    }

    @PostMapping("/add")
    public ModelAndView postAddOffer(@Valid OfferAddDTO offerAddDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddDTO", offerAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerAddDTO", bindingResult);

            return super.redirect("/offers/add");
        }

        this.offerService.addOffer(offerAddDTO);

        return super.redirect("/offers/all");
    }
}
