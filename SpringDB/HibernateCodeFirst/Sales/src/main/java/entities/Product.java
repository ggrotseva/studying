package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private int quantity;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product")
    private Set<Sale> sales;

//    @ManyToMany(mappedBy = "products")
//    private List<Customer> customers;
}
