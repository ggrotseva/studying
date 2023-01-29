package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.CarType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDTO {

    @XmlElement
    private CarType carType;

    @Size(min = 2, max = 30)
    @XmlElement
    private String carMake;

    @Size(min = 2, max = 30)
    @XmlElement
    private String carModel;

    @DecimalMin(value = "0", inclusive = false)
    @XmlElement
    private int year;

    @Size(min = 2, max = 30)
    @XmlElement
    private String plateNumber;

    @DecimalMin(value = "0", inclusive = false)
    @XmlElement
    private int kilometers;

    @DecimalMin(value = "1.0")
    @XmlElement
    private double engine;

    public CarSeedDTO() {
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }
}
