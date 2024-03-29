package bg.softuni.mobilelele.repository;

import bg.softuni.mobilelele.model.entities.UserRole;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(UserRoleEnum role);
}
