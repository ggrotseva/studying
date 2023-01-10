package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_location_id")
    private StoreLocation storeLocation;

}
