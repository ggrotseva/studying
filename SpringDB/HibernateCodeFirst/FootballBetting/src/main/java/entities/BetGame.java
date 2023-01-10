package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bet_games")
public class BetGame {

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "bet_id", referencedColumnName = "id")
    private Bet bet;

    @ManyToOne
    @JoinColumn(name = "result_prediction", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResultPrediction resultPrediction;
}
