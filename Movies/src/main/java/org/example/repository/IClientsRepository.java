package org.example.repository;

import org.example.domain.Clients;
import org.example.domain.validators.ValidatorException;

/*
 * Interface for CRUD operations on a Doctor.
 *
 */

public interface IClientsRepository {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return a {@code Clients}  with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    Clients findOne(Long id);

    /**
     * @return all entities.
     */
    Iterable<Clients> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Clients} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Clients save(Clients entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return a {@code Clients} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Clients delete(Long id);

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Clients} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Clients update(Clients entity) throws ValidatorException;
}
