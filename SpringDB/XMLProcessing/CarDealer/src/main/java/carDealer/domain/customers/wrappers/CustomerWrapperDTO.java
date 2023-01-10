package carDealer.domain.customers.wrappers;

import carDealer.domain.customers.CustomerDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerWrapperDTO {

    @XmlElement(name = "customer")
    private List<CustomerDTO> customers;

    public CustomerWrapperDTO() {
    }

    public CustomerWrapperDTO(List<CustomerDTO> customers) {
        this.customers = customers;
    }
}
