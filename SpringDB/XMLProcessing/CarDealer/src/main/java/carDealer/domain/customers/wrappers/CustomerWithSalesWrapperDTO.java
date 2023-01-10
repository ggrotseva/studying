package carDealer.domain.customers.wrappers;

import carDealer.domain.customers.CustomerWithSalesDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerWithSalesWrapperDTO {

    @XmlElement(name = "customer")
    private List<CustomerWithSalesDTO> customers;

    public CustomerWithSalesWrapperDTO() {
    }

    public CustomerWithSalesWrapperDTO(List<CustomerWithSalesDTO> customers) {
        this.customers = customers;
    }
}

