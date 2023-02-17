package bg.softuni.battleship.util.validations;

import bg.softuni.battleship.repositories.ShipRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueShipNameValidator implements ConstraintValidator<UniqueShipName, String> {

    private final ShipRepository shipRepository;

    public UniqueShipNameValidator(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return this.shipRepository.findByName(name).isEmpty();
    }
}
