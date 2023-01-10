package carDealer.repositories;

import carDealer.domain.parts.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    @Query(value = "SELECT * FROM car_dealer_xml.parts ORDER BY RAND() LIMIT :count", nativeQuery = true)
    Set<Part> getRandomParts(long count);
}
