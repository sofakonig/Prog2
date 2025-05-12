package at.ac.fhcampuswien.fhmdb.client;

public class MovieApiException extends RuntimeException {
    public MovieApiException(String message, Throwable cause) {
        super(message, cause);
    }
    public MovieApiException(String message) {
        super(message);
    }
}
