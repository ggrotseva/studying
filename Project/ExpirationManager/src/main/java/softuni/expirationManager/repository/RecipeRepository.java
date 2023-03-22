package softuni.expirationManager.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.expirationManager.model.entities.RecipeEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    Optional<RecipeEntity> findByName(String name);

    Optional<List<RecipeEntity>> findAllByOrderByCreatedDesc(Pageable pageable);

    // Unfortunately MatchesRegexp/REGEXP keywords are not supported in this Spring version/JPQL => Exception
    // so I have to do it with native query
//    @Query("SELECT r FROM RecipeEntity r WHERE r.ingredientsDescription REGEXP :productsNames")

    @Query(value = "SELECT * FROM recipes r WHERE r.ingredients_description REGEXP :productsNames", nativeQuery = true)
    Optional<Set<RecipeEntity>> findByIngredientsDescriptionMatchesRegex(String productsNames);

    Optional<Set<RecipeEntity>> findFirst25ByOrderByCreatedDesc();
}
