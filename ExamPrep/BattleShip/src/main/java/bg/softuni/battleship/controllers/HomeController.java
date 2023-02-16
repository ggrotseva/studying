package bg.softuni.battleship.controllers;

import bg.softuni.battleship.models.dto.BattleDTO;
import bg.softuni.battleship.models.dto.ShipDTO;
import bg.softuni.battleship.services.AuthService;
import bg.softuni.battleship.services.ShipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final AuthService authService;

    @Autowired
    public HomeController(ShipService shipService, AuthService authService) {
        this.shipService = shipService;
        this.authService = authService;
    }

    @ModelAttribute("battleDTO")
    public BattleDTO initBattleDTO() {
        return new BattleDTO();
    }

    @GetMapping("/")
    public String loggedOutIndex() {
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        Long loggedUserId = this.authService.getLoggedUserId();

        List<ShipDTO> ownShips = this.shipService.getShipsOwnedBy(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getShipsNotOwnedBy(loggedUserId);
        List<ShipDTO> allShips = this.shipService.getAllSorted();

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("allShipsSorted", allShips);

        return "home";
    }

    @PostMapping("/home")
    public String battle(@Valid BattleDTO battleDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("battleDTO", battleDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.battleDTO", bindingResult);

            return "redirect:/home";
        }

        this.shipService.attack(battleDTO);

        return "redirect:/home";
    }
}
