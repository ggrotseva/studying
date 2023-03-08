package softuni.expirationManager.repository;

import softuni.expirationManager.model.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // homepage
    Optional<List<ProductEntity>> findByCategoryUserUsername(String username);

    // homepage
    Optional<List<ProductEntity>> findByExpiryDateBeforeAndCategoryUserUsername(LocalDate date, String username);

    // table page
    Optional<List<ProductEntity>> findByCategoryId(Long id);
}
