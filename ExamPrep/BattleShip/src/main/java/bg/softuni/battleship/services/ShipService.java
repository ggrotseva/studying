package bg.softuni.battleship.services;

import bg.softuni.battleship.models.Category;
import bg.softuni.battleship.models.Ship;
import bg.softuni.battleship.models.ShipType;
import bg.softuni.battleship.models.User;
import bg.softuni.battleship.models.dto.CreateShipDTO;
import bg.softuni.battleship.repositories.CategoryRepository;
import bg.softuni.battleship.repositories.ShipRepository;
import bg.softuni.battleship.repositories.UserRepository;
import bg.softuni.battleship.session.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private LoggedUser loggedUser;

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
}
