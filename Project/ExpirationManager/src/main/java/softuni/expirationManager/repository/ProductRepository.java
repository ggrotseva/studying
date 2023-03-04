package softuni.expirationManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.expirationManager.model.ProductEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // homepage
    Optional<List<ProductEntity>> findByCategoryUserId(Long id);

    // homepage
    Optional<List<ProductEntity>> findByExpiryDateBeforeAndCategoryUserId(LocalDate date, Long id);

    // table page
    Optional<List<ProductEntity>> findByCategoryId(Long id);
}
