package errors;

public class RangeError extends RuntimeException {
    public RangeError(String range) {
        super("Unexpected value. Valid range: "  + range + " (RangeError)");
    }
}
