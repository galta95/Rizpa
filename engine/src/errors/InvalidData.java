package errors;

public class InvalidData extends RuntimeException  {
    public InvalidData(String message) {
            super(message + " (InvalidData)");
        }
}
