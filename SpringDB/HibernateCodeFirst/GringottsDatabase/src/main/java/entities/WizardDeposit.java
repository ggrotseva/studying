package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wizard_deposits")
public class WizardDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "wizard_id", referencedColumnName = "id")
    private Wizard wizard;

    @Column(name = "deposit_group", length = 20)
    private String depositGroup;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column
    private Double amount;

    @Column
    private Double interest;

    @Column
    private Double charge;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(length = 1000)
    private String notes;
}
