package org.example.domain.validators;


public class CinemaException extends RuntimeException{

    public CinemaException(String message) {
        super(message);
    }

    public CinemaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaException(Throwable cause) {
        super(cause);
    }
}
