package users.constants;

public enum Regexp {
    ;

    public static final String EMAIL_VALIDATION_REGEX =
            "^(?<user>[A-Za-z0-9]+[._-]*[A-Za-z0-9]+([._-]+[._-]*[A-Za-z0-9]+)*)@(?<host>[A-Za-z-]+\\.[A-Za-z-]+(\\.[A-Za-z-])*)$";

    public static final String PASSWORD_VALIDATION_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*,_+<>?])[A-Za-z\\d!@#$%^&*,_+<>?]{6,50}$";

}
