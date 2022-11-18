package org.example.repository;

import org.example.domain.Transaction;
import org.example.domain.validators.ValidatorException;

/*
 * Interface for CRUD operations on a Doctor.
 *
 */

public interface ITransactionRepository {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return a {@code Doctor}  with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    Transaction findOne(Long id);

    /**
     * @return all entities.
     */
    Iterable<Transaction> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Doctor} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Transaction save(Transaction entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return a {@code Doctor} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Transaction delete(Long id);

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Doctor} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Transaction update(Transaction entity) throws ValidatorException;
}
