package softuni.expirationManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.expirationManager.model.entities.RecipeEntity;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

//    @Query("SELECT r FROM RecipeEntity r WHERE r.ingredientsDescription REGEXP :productsNames")
    @Query(value = "SELECT * FROM recipes r WHERE r.ingredients_description REGEXP :productsNames", nativeQuery = true)
    Optional<Set<RecipeEntity>> findByIngredientsDescriptionMatchesRegex(String productsNames);

    Optional<Set<RecipeEntity>> findFirst25ByOrderByCreatedDesc();
}
