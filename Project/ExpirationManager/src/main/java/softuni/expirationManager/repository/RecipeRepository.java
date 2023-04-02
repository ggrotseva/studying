package softuni.expirationManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import softuni.expirationManager.model.entities.RecipeEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {

    // Unfortunately MatchesRegexp/REGEXP keywords are not supported in this Spring version/JPQL => Exception
    // so I have to do it with native query
//    @Query("SELECT r FROM RecipeEntity r WHERE r.ingredientsDescription REGEXP :productsNames")
    @Query(value = "SELECT * FROM recipes r WHERE r.ingredients_description REGEXP :productsNames", nativeQuery = true)
    Optional<Set<RecipeEntity>> findByIngredientsDescriptionMatchesRegex(String productsNames);

    Optional<Set<RecipeEntity>> findFirst25ByOrderByCreatedDesc();

    Page<RecipeEntity> findAllByOrderByCreatedDesc(Pageable pageable);

    Page<RecipeEntity> findAllByAuthorIdOrderByCreatedDesc(Long id, Pageable pageable);

    Page<RecipeEntity> findAll(Specification<RecipeEntity> spec, Pageable pageable);
}
