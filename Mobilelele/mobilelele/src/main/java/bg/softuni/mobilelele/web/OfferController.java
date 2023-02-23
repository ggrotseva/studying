package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.OfferAddDTO;
import bg.softuni.mobilelele.model.dto.OfferDTO;
import bg.softuni.mobilelele.model.dto.OfferDetailsDTO;
import bg.softuni.mobilelele.model.dto.OfferUpdateDTO;
import bg.softuni.mobilelele.service.BrandService;
import bg.softuni.mobilelele.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    public ModelAndView getAllOffers(Model model) {

        List<OfferDTO> allOffers = this.offerService.getAllOffers();

        model.addAttribute("allOffers", allOffers);

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

    @GetMapping("/update/{id}")
    public ModelAndView getUpdateOffer(@PathVariable Long id,
                                       Model model) {

        OfferUpdateDTO offerUpdateDTO = this.offerService.getOfferUpdateDTO(id);

        model.addAttribute("offerUpdateDTO", offerUpdateDTO);
        model.addAttribute("brands", this.brandService.getAllBrands());

        return super.view("update");
    }

    @PutMapping("/update/{id}")
    public ModelAndView getUpdateOffer(@Valid OfferUpdateDTO offerUpdateDTO,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerUpdateDTO", offerUpdateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerUpdateDTO", bindingResult);

            return super.redirect("/offers/update/{id}");
        }

        this.offerService.updateOffer(offerUpdateDTO);

        return super.redirect("/offers/all");
    }

    @GetMapping("/details/{id}")
    public ModelAndView getOfferDetails(@PathVariable Long id, Model model) {
        OfferDetailsDTO offer = this.offerService.getDetailedOfferById(id);

        model.addAttribute("offer", offer);

        return super.view("details");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getOfferDetails(@PathVariable Long id) {
        this.offerService.deleteById(id);

        return super.redirect("/offers/all");
    }
}
