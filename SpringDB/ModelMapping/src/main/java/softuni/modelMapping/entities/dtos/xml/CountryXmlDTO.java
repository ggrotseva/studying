package softuni.modelMapping.entities.dtos.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryXmlDTO {

    @XmlElement
    private long id;

    @XmlElement
    private String country;

    @XmlElementWrapper(name = "cities")
    @XmlElement(name = "city")
    private List<CityXmlDTO> cities;


    public CountryXmlDTO() {
    }

    public CountryXmlDTO(long id, String country, List<CityXmlDTO> cities) {
        this.id = id;
        this.country = country;
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CountryXmlDTO{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", cities=" + cities +
                '}';
    }
}
