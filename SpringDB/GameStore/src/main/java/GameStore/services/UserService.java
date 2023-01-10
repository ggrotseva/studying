package GameStore.services;

import GameStore.domain.entities.User;

public interface UserService {

    String registerUser(String[] tokens);

    String loginUser(String[] tokens);

    String logoutUser();

    String viewOwnedGames();

    User getLoggedInUser();
}
