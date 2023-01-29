package softuni.modelMapping.entities.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "city")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitiesXmlDTO {

    @XmlElement
    private List<String> city;

    public CitiesXmlDTO(String... names) {
        this.city = List.of(names[0], names[1], names[2]);
    }

    public CitiesXmlDTO() {
    }
}
