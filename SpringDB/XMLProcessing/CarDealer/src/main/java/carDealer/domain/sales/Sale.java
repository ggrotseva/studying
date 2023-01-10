package carDealer.domain.sales;

import carDealer.domain.cars.Car;
import carDealer.domain.customers.Customer;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn
    private Car car;

    @ManyToOne(optional = false)
    @JoinColumn
    private Customer customer;

    @Column
    private int discount;

    public Sale() {
    }

    public Sale(Car car, Customer customer, int discount) {
        this.car = car;
        this.customer = customer;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
