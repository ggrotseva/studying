package GameStore.repositories;

import GameStore.domain.dtos.GameViewDTO;
import GameStore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findFirstById(Long id);

    Optional<Game> findByTitle(String title);

    // Optional method to AllGames command
//    @Query("SELECT new GameStore.domain.dtos.GameViewDTO(g.title, g.price) FROM Game g")
//    Optional<Set<GameViewDTO>> viewAllGames();


}
