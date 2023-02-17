package bg.softuni.battleship.services;

import bg.softuni.battleship.models.Category;
import bg.softuni.battleship.models.Ship;
import bg.softuni.battleship.models.ShipType;
import bg.softuni.battleship.models.User;
import bg.softuni.battleship.models.dto.BattleDTO;
import bg.softuni.battleship.models.dto.CreateShipDTO;
import bg.softuni.battleship.models.dto.ShipDTO;
import bg.softuni.battleship.repositories.CategoryRepository;
import bg.softuni.battleship.repositories.ShipRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryService categoryService;
    private final AuthService authService;
    private final ModelMapper mapper;

    @Autowired
    public ShipService(ShipRepository shipRepository,
                       CategoryService categoryService,
                       AuthService authService,
                       ModelMapper mapper) {
        this.shipRepository = shipRepository;
        this.categoryService = categoryService;
        this.authService = authService;
        this.mapper = mapper;
    }

    public boolean create(CreateShipDTO createShipDTO) {

        ShipType type = ShipType.values()[createShipDTO.getCategory()];
        Category category = this.categoryService.getByName(type);

        User owner = this.authService.findById(authService.getLoggedUserId());

        Ship ship = mapper.map(createShipDTO, Ship.class)
                .setCategory(category)
                .setOwner(owner);

        this.shipRepository.save(ship);

        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(Long ownerId) {
        return this.shipRepository.findByUserId(ownerId)
                .stream().map(ship -> this.mapper.map(ship, ShipDTO.class))
                .collect(Collectors.toList());
    }

    public List<ShipDTO> getShipsNotOwnedBy(Long userId) {
        return this.shipRepository.findByUserIdNot(userId)
                .stream().map(ship -> this.mapper.map(ship, ShipDTO.class))
                .collect(Collectors.toList());
    }

    public List<ShipDTO> getAllSorted() {
        return this.shipRepository.findAllByOrderByHealthAscNameAscPowerAsc()
                .stream().map(ship -> this.mapper.map(ship, ShipDTO.class))
                .collect(Collectors.toList());
    }

    public void attack(BattleDTO attackData) {
        Optional<Ship> attackerOpt = this.shipRepository.findById(attackData.getAttackerId());
        Optional<Ship> defenderOpt = this.shipRepository.findById(attackData.getDefenderId());

        if (attackerOpt.isEmpty() || defenderOpt.isEmpty()) {
            return;
        }

        Ship defender = defenderOpt.get();

        long newDefenderHealth = defender.getHealth() - attackerOpt.get().getPower();

        if (newDefenderHealth <= 0) {
            this.shipRepository.deleteById(defender.getId());

        } else {

            defender.setHealth(newDefenderHealth);
            this.shipRepository.save(defender);
        }

    }
}
