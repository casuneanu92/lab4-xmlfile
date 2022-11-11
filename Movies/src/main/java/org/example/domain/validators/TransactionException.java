package org.example.domain.validators;

/**
 * Created by radu.
 */
public class TransactionException extends RuntimeException{

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
