package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.entities.Category;
import bg.softuni.pathfinder.model.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Optional<Route> findFirstByOrderByCommentsDesc();

    Optional<List<Route>> findByCategoriesContains(Category category);

}
