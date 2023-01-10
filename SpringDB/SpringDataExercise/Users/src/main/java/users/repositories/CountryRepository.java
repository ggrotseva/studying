package users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
