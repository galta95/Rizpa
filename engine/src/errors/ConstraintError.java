package errors;

public class ConstraintError extends RuntimeException {
    public ConstraintError(String element) {
        super("'" + element + "' already exits (ConstraintError)");
    }
}
