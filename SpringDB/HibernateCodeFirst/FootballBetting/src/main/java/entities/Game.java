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
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "home_team")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team")
    private Team awayTeam;

    @Column(name = "home_team_goals")
    private Short homeGoals;

    @Column(name = "away_team_goals")
    private Short awayGoals;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;

    @Column(name = "home_team_win_bet_rate")
    private Double homeTeamBetRate;

    @Column(name = "away_team_win_bet_rate")
    private Double awayTeamBetRate;

    @Column(name = "draw_game_bet_rate")
    private Double drawGameBetRate;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
}
