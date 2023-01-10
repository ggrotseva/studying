package GameStore.constants;

public enum Validations {
    ;

    // User validation messages
    public static final String EMAIL_PATTERN =
            "^([a-zA-Z0-9][a-zA-Z0-9_.-]+)@([a-zA-Z0-9-]+)(?:\\.[a-zA-Z0-9]+)+$";
    public static final String INVALID_EMAIL_MESSAGE = "Incorrect email.";
    public static final String EMAIL_EXISTS_MESSAGE = "User with this email already exists";

    public static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
    public static final String INVALID_PASSWORD_FORMAT_MESSAGE = "Invalid password. Password should have at least 6 letters, " +
            "1 uppercase, 1 lowercase and 1 digit.";
    public static final String PASSWORD_CONFIRM_FAILED_MESSAGE = "Password confirmation failed";

    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username / password";

    public static final String CANNOT_LOGOUT_MESSAGE = "Cannot log out. ";
    public static final String NO_LOGGED_USER = "No user was logged in.";

    // Game validation messages
    public static final String INVALID_GAME_TITLE_MESSAGE = "Not a valid game title";
    public static final String INVALID_TRAILER_MESSAGE = "Not a valid trailer id";
    public static final String INVALID_IMAGE_URL_MESSAGE = "Not a valid image url";
    public static final String INVALID_SIZE_MESSAGE = "Game size should be a positive number";
    public static final String INVALID_PRICE_MESSAGE = "Price should be a positive number";
    public static final String INVALID_DESCRIPTION_MESSAGE = "Description should be at least 20 symbols long";

    public static final String INVALID_ID_MESSAGE = "Invalid game ID";
    public static final String NO_SUCH_GAME_MESSAGE = "No game with such title found";

    // Common messages
    public static final String NO_ADMIN_RIGHTS_MESSAGE = "You have no admin rights!";
    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found";
}
