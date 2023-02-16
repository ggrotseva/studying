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
import bg.softuni.battleship.repositories.UserRepository;
import bg.softuni.battleship.session.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public ShipService(ShipRepository shipRepository,
                       CategoryRepository categoryRepository,
                       UserRepository userRepository,
                       LoggedUser loggedUser) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }


    public boolean create(CreateShipDTO createShipDTO) {

        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());

        if (byName.isPresent()) {
            return false;
        }

        ShipType type = ShipType.values()[createShipDTO.getCategory()];
        Category category = this.categoryRepository.findByName(type);

        User owner = this.userRepository.findById(loggedUser.getId()).get();

        Ship ship = new Ship()
                .setName(createShipDTO.getName())
                .setPower(createShipDTO.getPower())
                .setHealth(createShipDTO.getHealth())
                .setCreated(createShipDTO.getCreated())
                .setCategory(category)
                .setOwner(owner);

        this.shipRepository.save(ship);

        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(Long ownerId) {
        return this.shipRepository.findByUserId(ownerId)
                .stream().map(ShipDTO::new)
                .collect(Collectors.toList());
    }

    public List<ShipDTO> getShipsNotOwnedBy(Long userId) {
        return this.shipRepository.findByUserIdNot(userId)
                .stream().map(ShipDTO::new)
                .collect(Collectors.toList());
    }

    public List<ShipDTO> getAllSorted() {
        return this.shipRepository.findAllByOrderByHealthAscNameAscPowerAsc()
                .stream().map(ShipDTO::new)
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
