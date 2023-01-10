package exceptions;

public class RequiredDataException extends RuntimeException {

    public RequiredDataException(String message) {
        super(message);
    }
}
