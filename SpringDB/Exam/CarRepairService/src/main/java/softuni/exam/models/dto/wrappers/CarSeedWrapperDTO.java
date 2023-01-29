package softuni.exam.models.dto.wrappers;

import softuni.exam.models.dto.CarSeedDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedWrapperDTO {

    @XmlElement(name = "car")
    private List<CarSeedDTO> cars;

    public CarSeedWrapperDTO() {
    }

    public List<CarSeedDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarSeedDTO> cars) {
        this.cars = cars;
    }
}
