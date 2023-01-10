package carDealer.services.customers;

import carDealer.domain.customers.CustomerDTO;
import carDealer.domain.customers.CustomerWithSalesDTO;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllByOrderByBirthDateIsYoungDriver() throws IOException;

    List<CustomerWithSalesDTO> getAllWithSales() throws IOException;

}
