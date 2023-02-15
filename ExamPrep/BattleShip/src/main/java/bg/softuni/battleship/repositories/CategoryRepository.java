package bg.softuni.battleship.repositories;

import bg.softuni.battleship.models.Category;
import bg.softuni.battleship.models.Ship;
import bg.softuni.battleship.models.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(ShipType name);
}
