package intler_iot.services.exceptions;

public class NotAuthException extends Exception {
    public NotAuthException() {
        super();
    }

    public NotAuthException(String message) {
        super(message);
    }

    public NotAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthException(Throwable cause) {
        super(cause);
    }
}
