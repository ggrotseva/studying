package carDealer.domain.cars.wrappers;

import carDealer.domain.cars.CarPartsDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarPartsWrapperDTO {

    @XmlElement(name = "car")
    private List<CarPartsDTO> cars;

    public CarPartsWrapperDTO() {
    }

    public CarPartsWrapperDTO(List<CarPartsDTO> cars) {
        this.cars = cars;
    }
}
