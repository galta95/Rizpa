package Errors;

public class FileFormatNotSupportedError extends RuntimeException {
    public FileFormatNotSupportedError(String format) {
        super("Not " + format + " file (FileFormatNotSupportedError)");
    }
}
