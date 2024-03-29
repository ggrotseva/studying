package bg.softuni.battleship.repositories;

import bg.softuni.battleship.models.Category;
import bg.softuni.battleship.models.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category getByName(ShipType name);

}
