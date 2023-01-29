package softuni.exam.models.dto;

import softuni.exam.util.LocalDateTimeXmlTypeAdapter;

import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedDTO {

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal price;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeXmlTypeAdapter.class)
    private LocalDateTime date;

    @XmlElement
    private MechanicFirstNameDTO mechanic;

    @XmlElement
    private PartIdDTO part;

    @XmlElement
    private CarIdDTO car;

    public TaskSeedDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public MechanicFirstNameDTO getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicFirstNameDTO mechanic) {
        this.mechanic = mechanic;
    }

    public PartIdDTO getPart() {
        return part;
    }

    public void setPart(PartIdDTO part) {
        this.part = part;
    }

    public CarIdDTO getCar() {
        return car;
    }

    public void setCar(CarIdDTO car) {
        this.car = car;
    }
}
