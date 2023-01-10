package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {

    @Column
    private String cardType;

    @Column
    @Enumerated(EnumType.STRING)
    private Month expirationMonth;

    @Column
    private Year expirationYear;
}
