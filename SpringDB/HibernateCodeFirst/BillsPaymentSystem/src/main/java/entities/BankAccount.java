package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail {

    @Column
    private String bankName;

    @Column(length = 11)
    private String swiftCode;

}
