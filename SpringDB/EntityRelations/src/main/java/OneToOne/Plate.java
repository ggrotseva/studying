package OneToOne;

import jakarta.persistence.*;

@Entity
@Table(name = "registration_plates")
public class Plate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String plateNumber;

//    @OneToOne(mappedBy = "plate")
//    private Car car;
}
