package Errors;

public class DataNotLoadedError extends RuntimeException {
    public DataNotLoadedError() {
        super("Must load Xml first (DataNotLoadedError)");
    }
}
