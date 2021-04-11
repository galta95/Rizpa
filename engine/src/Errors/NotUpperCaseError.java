package Errors;

public class NotUpperCaseError extends RuntimeException {
    public NotUpperCaseError(String element) {
        super("'" + element + "' is not only letters or upper case (NotUpperCaseError)");
    }
}