package GameStore.services;

import GameStore.domain.entities.Game;
import GameStore.domain.entities.Order;
import GameStore.domain.entities.User;
import GameStore.repositories.GameRepository;
import GameStore.repositories.OrderRepository;
import GameStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static GameStore.constants.Validations.*;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ADDED_ITEM_FORMAT = "%s added to cart.";
    private static final String REMOVED_ITEM_FORMAT = "%s removed from cart.";
    private static final String SUCCESSFULLY_BOUGHT_FORMAT = "Successfully bought games:%n -%s";

    private Order order;

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, GameRepository gameRepository, UserRepository userRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String addItem(String gameTitle) {
        User loggedInUser = this.userService.getLoggedInUser();

        if (loggedInUser == null) {
            return NO_LOGGED_USER;
        }

        if (this.order == null || !this.order.getUser().equals(loggedInUser)) {
            this.order = new Order(loggedInUser);
        }

        if (this.gameRepository.findByTitle(gameTitle).isEmpty()) {
            return NO_SUCH_GAME_MESSAGE;
        }

        final Game gameFromDb = this.gameRepository.findByTitle(gameTitle).get();

        if (loggedInUser.getGames().contains(gameFromDb)) {
            return ALREADY_PURCHASED_MESSAGE;
        }

        this.order.addGame(gameFromDb);

        return String.format(ADDED_ITEM_FORMAT, gameFromDb.getTitle());
    }

    @Override
    public String removeItem(String gameTitle) {
        User loggedInUser = this.userService.getLoggedInUser();

        if (loggedInUser == null) {
            return NO_LOGGED_USER;
        }

        if (this.order == null || !this.order.getUser().equals(loggedInUser)) {
            return ORDER_EMPTY_MESSAGE;
        }

        if (this.gameRepository.findByTitle(gameTitle).isEmpty()) {
            return NO_SUCH_GAME_MESSAGE;
        }

        final Game gameFromDb = this.gameRepository.findByTitle(gameTitle).get();

        boolean doesGameExistInOrder = this.order.getGames().stream()
                .anyMatch(game -> game.equals(gameFromDb));

        if (!doesGameExistInOrder) {
            return NO_SUCH_GAME_IN_CART_MESSAGE;
        }

        this.order.removeGame(gameFromDb);

        return String.format(REMOVED_ITEM_FORMAT, gameFromDb.getTitle());
    }

    @Override
    public String buyItem() {
        User loggedInUser = this.userService.getLoggedInUser();

        if (loggedInUser == null) {
            return NO_LOGGED_USER;
        }

        if (this.order == null || !this.order.getUser().equals(loggedInUser)) {
            return ORDER_EMPTY_MESSAGE;
        }

        this.orderRepository.save(this.order);

        Set<Game> boughtGames = this.order.getGames();

        loggedInUser.addGames(boughtGames);
        this.userRepository.save(loggedInUser);

        String gameTitles = this.order.getGames().stream()
                .map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator() + " -"));

        setOrderToNull();

        return String.format(SUCCESSFULLY_BOUGHT_FORMAT, gameTitles);
    }

    @Override
    public void setOrderToNull() {
        this.order = null;
    }


}
