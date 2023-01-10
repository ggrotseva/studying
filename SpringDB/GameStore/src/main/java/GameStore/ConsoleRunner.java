package GameStore;

import GameStore.services.GameService;
import GameStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static GameStore.constants.Commands.*;
import static GameStore.constants.Validations.*;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final Scanner scanner;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.scanner = new Scanner(System.in);
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) {
        String input = scanner.nextLine();

        while (!input.equals("close")) {
            String[] tokens = input.split("\\|");

            executeCommand(tokens);

            input = scanner.nextLine();
        }
    }

    private void executeCommand(String[] tokens) {
        String command = tokens[0];

        String output = switch (command) {
            case REGISTER_USER -> this.userService.registerUser(tokens);
            case LOGIN_USER -> this.userService.loginUser(tokens);
            case LOGOUT -> this.userService.logoutUser();
            case ADD_GAME -> this.gameService.addGame(tokens);
            case EDIT_GAME -> this.gameService.editGame(tokens);
            case DELETE_GAME -> this.gameService.deleteGame(Long.parseLong(tokens[1]));
            case VIEW_ALL_GAMES -> this.gameService.viewAllGames();

            // command in softuni's Example in .docx file is DetailGame, not DetailsGame
            case DETAILS_GAME -> this.gameService.viewGameDetails(tokens[1]);
            case OWNED_GAMES -> this.userService.viewOwnedGames();

            default -> COMMAND_NOT_FOUND_MESSAGE;
        };

        System.out.println(output);
    }
}
