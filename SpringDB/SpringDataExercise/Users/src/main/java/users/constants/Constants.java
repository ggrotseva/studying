package users.constants;

public enum Constants {
    ;

    public static final String EMAIL_VALIDATION_REGEX =
            "^(?<user>[A-Za-z0-9]+[._-]*[A-Za-z0-9]+([._-]+[._-]*[A-Za-z0-9]+)*)@(?<host>[A-Za-z-]+\\.[A-Za-z-]+(\\.[A-Za-z-])*)$";

    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String INVALID_EMAIL_NULL = "Email cannot be null";



    public static final String PASSWORD_VALIDATION_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*,_+<>?])[A-Za-z\\d!@#$%^&*,_+<>?]{6,50}$";

    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String INVALID_PASSWORD_NULL = "Password cannot be null";
    public static final String PASSWORD_TOO_SHORT = "Password too short";
    public static final String PASSWORD_TOO_LONG = "Password too long";
    public static final String PASSWORD_DIGIT_ERROR = "Password should contain at least one digit";
    public static final String PASSWORD_UPPER_ERROR = "Password should contain at least one uppercase letter";
    public static final String PASSWORD_LOWER_ERROR = "Password should contain at least one lowercase letter";
    public static final String PASSWORD_SPECIAL_SYMBOL_ERROR = "Password should contain at least one special character";


    public static final String AGE_TOO_LOW = "Age cannot be zero or less";
    public static final String AGE_TOO_HIGH = "Age cannot be above 120";


    public static final String USERNAME_LENGTH_MESSAGE = "Username should be between 4 and 30 characters long";

}
