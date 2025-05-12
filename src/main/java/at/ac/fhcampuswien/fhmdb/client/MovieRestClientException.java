package at.ac.fhcampuswien.fhmdb.client;

public class MovieRestClientException extends RuntimeException {
    public MovieRestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieRestClientException(String message) {
        super(message);
    }
}
