package users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import users.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByEmailEndingWith(String emailProvider);

    int countByLastTimeLoggedInBefore(LocalDateTime dateTime);

    @Query("UPDATE User u SET u.isDeleted = 1 WHERE u.lastTimeLoggedIn < :dateString")
    @Modifying
    void updateIsDeletedUserLoggedInBefore(LocalDateTime dateString);

    void deleteByIsDeletedIsTrue();
}
