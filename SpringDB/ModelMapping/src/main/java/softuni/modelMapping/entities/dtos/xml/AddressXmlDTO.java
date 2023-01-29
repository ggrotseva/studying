package softuni.modelMapping.entities.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressXmlDTO {

    @XmlElement
    private int id;

    @XmlElement
    private String country;

    @XmlElement
    private String city;

    public AddressXmlDTO() {
    }

    public AddressXmlDTO(int id, String country, String city) {
        this.id = id;
        this.country = country;
        this.city = city;
    }

}
