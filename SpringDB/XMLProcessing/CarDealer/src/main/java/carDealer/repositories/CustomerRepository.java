package carDealer.repositories;

import carDealer.domain.customers.Customer;
import carDealer.domain.customers.CustomerWithSalesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrderByBirthDateAscIsYoungDriverAsc();

    @Query("SELECT new carDealer.domain.customers.CustomerWithSalesDTO(cust.name, COUNT(c), SUM((p.price)*(1-s.discount/100))) " +
            "FROM Sale s " +
            "JOIN s.car c " +
            "JOIN s.car.parts p " +
            "JOIN s.customer cust " +
            "GROUP BY cust " +
            "ORDER BY SUM((p.price)*(1-s.discount/100)) DESC, COUNT(c) DESC")
    List<CustomerWithSalesDTO> getAllWithSales();


}
