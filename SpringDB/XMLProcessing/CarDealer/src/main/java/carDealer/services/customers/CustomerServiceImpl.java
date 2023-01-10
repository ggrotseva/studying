package carDealer.services.customers;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.customers.CustomerDTO;
import carDealer.domain.customers.CustomerWithSalesDTO;
import carDealer.domain.customers.wrappers.CustomerWithSalesWrapperDTO;
import carDealer.domain.customers.wrappers.CustomerWrapperDTO;
import carDealer.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDTO> findAllByOrderByBirthDateIsYoungDriver() throws JAXBException {
        final List<CustomerDTO> customers = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc()
                .stream().map(cust -> mapper.map(cust, CustomerDTO.class))
                .collect(Collectors.toList());

        final CustomerWrapperDTO wrapperDTO = new CustomerWrapperDTO(customers);

        Utils.writeXmlIntoFile(wrapperDTO, OutputPaths.ORDERED_CUSTOMERS);

        return customers;
    }

    @Override
    public List<CustomerWithSalesDTO> getAllWithSales() throws JAXBException {
        List<CustomerWithSalesDTO> customersSalesDtos = this.customerRepository.getAllWithSales();

        final CustomerWithSalesWrapperDTO wrapperDTO = new CustomerWithSalesWrapperDTO(customersSalesDtos);

        Utils.writeXmlIntoFile(wrapperDTO, OutputPaths.CUSTOMERS_TOTAL_SALES);

        return customersSalesDtos;
    }

}
