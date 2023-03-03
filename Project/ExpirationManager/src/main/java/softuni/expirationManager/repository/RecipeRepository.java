package softuni.expirationManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.expirationManager.model.RecipeEntity;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
}
