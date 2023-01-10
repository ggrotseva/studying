package productShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productShop.domain.entities.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM product_shop.categories ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Category> getRandomCategory();
}
