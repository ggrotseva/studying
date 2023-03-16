package softuni.expirationManager.repository;

import softuni.expirationManager.model.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<List<ProductEntity>> findAllByExpiryDateBeforeAndCategoryUserUsername(LocalDate date, String username);

    Optional<List<ProductEntity>> findAllByCategoryId(Long id);
}
