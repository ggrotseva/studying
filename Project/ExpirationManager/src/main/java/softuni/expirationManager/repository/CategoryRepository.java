package softuni.expirationManager.repository;

import softuni.expirationManager.model.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<List<CategoryEntity>> findAllByUserUsername(String username);

    Optional<CategoryEntity> findByName(String name);
}
