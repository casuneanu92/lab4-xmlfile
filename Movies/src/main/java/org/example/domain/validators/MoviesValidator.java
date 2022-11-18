package org.example.domain.validators;

import org.example.domain.Movies;

public class MoviesValidator implements Validator<Movies> {
    /**
     * Validates a year
     * @param movies the movies
     * @throws ValidatorException if the year is not valide
     */
    public void validate(Movies movies) throws ValidatorException {
        if (movies.getYear() < 0) {
            throw new ValidatorException("The age must be positive!");
        }
    }
}
