package org.example.repository;

import org.example.domain.Movies;
import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class MoviesRepository implements IMoviesRepository{
    private Map<Long, Movies> entities;
    private Validator<Movies> validator;

    public MoviesRepository(Map<Long, Movies> entities, Validator<Movies> validator) {
        this.entities = entities;
        this.validator = validator;
    }

    public MoviesRepository() {
        this.entities = new HashMap<>();
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return a {@code Patient}  with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Movies findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return entities.get(id);
        //throw new RuntimeException("not yet implemented");
    }

    /**
     * @return all entities.
     */
    @Override
    public Iterable<Movies> findAll() {
        Set<Movies> patients = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return patients;
    }

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Patient} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    @Override
    public Movies save(Movies entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return entities.putIfAbsent(entity.getIdEntity(), entity);
    }

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return a {@code Patient} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Movies delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return entities.remove(id);
        //throw new RuntimeException("not yet implemented");
    }

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Patient} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    @Override
    public Movies update(Movies entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return entities.computeIfPresent(entity.getIdEntity(), (k, v) -> entity);
        //throw new RuntimeException("not yet implemented");
    }

}
