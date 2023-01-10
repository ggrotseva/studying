package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "result_predictions")
public enum ResultPrediction {

    HOME_TEAM_WIN("Home Team Win"),
    DRAW_GAME("Draw Game"),
    AWAY_TEAM_WIN("Away Team Win");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String prediction;

    ResultPrediction(String prediction) {
        this.prediction = prediction;
    }
}



