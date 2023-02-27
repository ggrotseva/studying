package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.BrandWithModelsDTO;
import bg.softuni.mobilelele.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public ModelAndView viewAllBrands(ModelAndView mav) {
        mav.setViewName("brands");

        List<BrandWithModelsDTO> brandsWithModels = this.brandService.getAllBrandsWithModelsInfo();

        mav.addObject("brandsWithModels", brandsWithModels);

        return mav;
    }
}
