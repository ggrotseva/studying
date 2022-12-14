package users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.models.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

}
