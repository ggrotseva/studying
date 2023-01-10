package GameStore.services;

public interface GameService {

    String addGame(String[] tokens);

    String editGame(String[] tokens);

    String deleteGame(Long id);

    String viewAllGames();

    String viewGameDetails(String title);
}
