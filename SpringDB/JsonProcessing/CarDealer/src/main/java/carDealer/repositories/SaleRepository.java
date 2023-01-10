package carDealer.repositories;

import carDealer.domain.sales.Sale;
import carDealer.domain.sales.SaleWithDiscountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new carDealer.domain.sales.SaleWithDiscountDTO(" +
            "c, cust.name, 0.01*s.discount, SUM(p.price), SUM((p.price)*(1-s.discount/100))) " +
            "FROM Sale s " +
            "JOIN s.car c " +
            "JOIN s.car.parts p " +
            "JOIN s.customer cust " +
            "GROUP BY cust")
    List<SaleWithDiscountDTO> getAllWithDiscounts();
}
