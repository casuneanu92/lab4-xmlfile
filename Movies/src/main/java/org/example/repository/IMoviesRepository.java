package org.example.repository;

import org.example.domain.Movies;
import org.example.domain.validators.ValidatorException;

import java.util.Map;

/*
 * Interface for CRUD operations on a Patient.
 *
 */

public interface IMoviesRepository {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return a {@code Movies}  with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    Movies findOne(Long id);

    /**
     * @return all entities.
     */
    Iterable<Movies> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Movies} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Movies save(Movies entity);

    Movies save(Map entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return a {@code Movies} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Movies delete(Long id);

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Movies} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    Movies update(Movies entity);
}
