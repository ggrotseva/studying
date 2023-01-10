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
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;

//    @ManyToMany
//    @JoinTable(name = "customers_products",
//    joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
//    private List<Product> products;
}
