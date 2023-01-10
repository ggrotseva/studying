package carDealer.services.customers;

import carDealer.domain.customers.CustomerDTO;
import carDealer.domain.customers.CustomerWithSalesDTO;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllByOrderByBirthDateIsYoungDriver() throws JAXBException;

    List<CustomerWithSalesDTO> getAllWithSales() throws JAXBException;

}
