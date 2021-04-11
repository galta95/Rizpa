package Errors;

public class NotFoundError extends RuntimeException {
    public NotFoundError(String element) {
        super("'" + element + "' not found (NotFoundError)");
    }
}
