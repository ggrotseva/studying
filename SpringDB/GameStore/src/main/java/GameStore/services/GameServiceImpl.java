package GameStore.services;

import GameStore.domain.dtos.GameDTO;
import GameStore.domain.dtos.GameViewDTO;
import GameStore.domain.entities.Game;
import GameStore.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static GameStore.constants.Validations.*;

@Service
public class GameServiceImpl implements GameService {

    public static final String GAME_ADDED_FORMAT = "Added %s";
    public static final String GAME_DELETED_MESSAGE = "Deleted %s";
    public static final String GAME_EDITED_MESSAGE = "Edited %s";
    public static final String INVALID_FIELD_MESSAGE = "Invalid field name in edit command";

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper mapper) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public String addGame(String[] tokens) {
        if (isAdminLogged()) {
            String title = tokens[1];
            BigDecimal price = new BigDecimal(tokens[2]);
            float size = Float.parseFloat(tokens[3]);
            String trailerId = tokens[4];
            String imageUrl = tokens[5];
            String description = tokens[6];
            LocalDate releaseDate = LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            GameDTO game;

            try {
                game = new GameDTO(title, trailerId, imageUrl, size, price, description, releaseDate);
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }

            Game gameToAdd = mapper.map(game, Game.class);

            this.gameRepository.save(gameToAdd);

            return String.format(GAME_ADDED_FORMAT, title);
        }

        return NO_ADMIN_RIGHTS_MESSAGE;
    }

    @Override
    @Transactional
    public String editGame(String[] tokens) {
        if (isAdminLogged()) {
            Long id = Long.parseLong(tokens[1]);

            if (this.gameRepository.findFirstById(id).isEmpty()) {
                return INVALID_ID_MESSAGE;
            }

            final Game gameFromDB = this.gameRepository.findFirstById(id).get();

            GameDTO gameDTO = mapper.map(gameFromDB, GameDTO.class);

            try {
                for (int i = 2; i < tokens.length; i++) {
                    parseEditCommand(gameDTO, tokens[i]);
                }
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }

            final Game game = mapper.map(gameDTO, Game.class);

            this.gameRepository.save(game);

            return String.format(GAME_EDITED_MESSAGE, gameFromDB.getTitle());
        }

        return NO_ADMIN_RIGHTS_MESSAGE;
    }

    @Override
    @Transactional
    public String deleteGame(Long id) {
        if (isAdminLogged()) {

            Optional<Game> game = this.gameRepository.findFirstById(id);

            if (game.isEmpty()) {
                return INVALID_ID_MESSAGE;
            }

            final String gameTitle = game.get().getTitle();

            this.gameRepository.deleteById(id);

            return String.format(GAME_DELETED_MESSAGE, gameTitle);
        }

        return NO_ADMIN_RIGHTS_MESSAGE;
    }

    @Override
    public String viewAllGames() {
//        if (isNoUserLogged()) {
//            return NO_LOGGED_USER;
//        }

        return this.gameRepository.findAll().stream()
                .map(game -> mapper.map(game, GameViewDTO.class))
                .map(GameViewDTO::toTitleAndPrice)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String viewGameDetails(String title) {
//        if (isNoUserLogged()) {
//            return NO_LOGGED_USER;
//        }

        Optional<Game> gameFromDB = this.gameRepository.findByTitle(title);

        if (gameFromDB.isPresent()) {

            GameViewDTO gameDTO = mapper.map(gameFromDB, GameViewDTO.class);

            return gameDTO.getAllInfo();
        }

        return NO_SUCH_GAME_MESSAGE;
    }

    private boolean isAdminLogged() {
        return userService.getLoggedInUser() != null && userService.getLoggedInUser().isAdmin();
    }

    private boolean isNoUserLogged() {
        return userService.getLoggedInUser() == null;
    }

    @Transactional
    private void parseEditCommand(GameDTO game, String command) {
        String[] commandTokens = command.split("=");

        switch (commandTokens[0].toLowerCase(Locale.ROOT)) {
            case "title" -> game.setTitle(commandTokens[1]);
            case "price" -> game.setPrice(new BigDecimal(commandTokens[1]));
            case "size" -> game.setSize(Float.parseFloat(commandTokens[1]));
            case "trailer" -> game.setTrailerId(commandTokens[1]);
            case "thumbnailurl" -> game.setImageUrl(commandTokens[1]);
            case "description" -> game.setDescription(commandTokens[1]);
            case "releasedate" -> game.setReleaseDate(LocalDate.parse(commandTokens[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            default -> throw new IllegalArgumentException(INVALID_FIELD_MESSAGE);
        }

    }
}
