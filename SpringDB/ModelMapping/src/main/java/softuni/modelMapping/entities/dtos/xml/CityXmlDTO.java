package softuni.modelMapping.entities.dtos.xml;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "city")
@XmlAccessorType(XmlAccessType.FIELD)
public class CityXmlDTO implements Serializable {

    @XmlAttribute
    private String name;

    public CityXmlDTO(String name) {
        this.name = name;
    }

    public CityXmlDTO() {
    }

}
