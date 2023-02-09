package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);
}
