package hasEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "has_cars")
public class HasCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plate;

    public HasCar() {
    }

    public HasCar(PlateNumber plate) {
        this.plate = plate;
    }
}
