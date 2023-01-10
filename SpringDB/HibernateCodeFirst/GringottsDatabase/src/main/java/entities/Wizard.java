package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wizards")
public class Wizard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @OneToOne
    @JoinColumn(name = "magic_wand_id", referencedColumnName = "id")
    private MagicWand magicWand;

    @OneToMany(mappedBy = "wizard")
    List<WizardDeposit> deposits;

}
