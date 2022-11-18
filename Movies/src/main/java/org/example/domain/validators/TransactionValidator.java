package org.example.domain.validators;


import org.example.domain.Transaction;

public class TransactionValidator implements Validator<Transaction> {
    /**
     * Validates a consultation
     * @param transaction the consultation
     * @throws ValidatorException if the consultation is not valide
     */
    public void validate(Transaction transaction) throws ValidatorException {
        if (transaction.getPrice() < 0) {
            throw new ValidatorException("The price must be >=0!");
        }
    }
}

