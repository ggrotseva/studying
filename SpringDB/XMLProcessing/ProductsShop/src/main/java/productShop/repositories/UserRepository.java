package productShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productShop.domain.dtos.user.UserAgeWithProductsDTO;
import productShop.domain.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u" +
            " JOIN FETCH u.sellingProducts p" +
            " WHERE p.buyer IS NOT NULL" +
            " ORDER BY u.lastName, u.firstName")
    Optional<List<User>> findAllWithSoldProducts();

    @Query("SELECT DISTINCT u FROM User u" +
            " JOIN FETCH u.sellingProducts AS sold" +
            " WHERE sold.buyer IS NOT NULL")
    // the ordering is in the wrapper DTO
    Optional<List<User>> findAllWithSoldProductsOrderByCount();

}