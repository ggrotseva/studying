package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "competition_types")
public enum CompetitionType {

    LOCAL("Local"),
    NATIONAL("National"),
    INTERNATIONAL("International");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String type;

    CompetitionType(String type) {
        this.type = type;
    }
}
