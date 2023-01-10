package carDealer.domain.customers.wrappers;

import carDealer.domain.customers.CustomerImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerImportWrapperDTO {

    @XmlElement(name = "customer")
    private List<CustomerImportDTO> customers;

    public CustomerImportWrapperDTO() {
    }

    public List<CustomerImportDTO> getCustomers() {
        return customers;
    }
}
