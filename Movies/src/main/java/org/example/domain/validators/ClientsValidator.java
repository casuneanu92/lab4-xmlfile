package org.example.domain.validators;

import org.example.domain.Clients;

public class ClientsValidator implements Validator<Clients> {
    @Override
    public void validate(Clients entity) throws ValidatorException {
        if (entity.getAge() < 25) {
            throw new ValidatorException("The age must be >= 25!");
        }
    }
}


