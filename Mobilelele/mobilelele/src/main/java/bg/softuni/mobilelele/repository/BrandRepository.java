package bg.softuni.mobilelele.repository;

import bg.softuni.mobilelele.model.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
