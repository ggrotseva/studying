package bg.softuni.mobilelele.repository;

import bg.softuni.mobilelele.model.userRole.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}