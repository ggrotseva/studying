package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String logo;

    @Column(length = 3, nullable = false)
    private String initials;

    @ManyToOne
    @JoinColumn(name = "primary_kit_color")
    private Color primaryKitColor;

    @ManyToOne
    @JoinColumn(name = "secondary_kit_color")
    private Color secondaryKitColor;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @Column
    private BigDecimal budget;
}
