package org.example.repository;

import org.example.domain.Clients;
import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientsRepository implements IClientsRepository{
    private Map<Long, Clients> entities;
    private Validator<Clients> validator;

    public ClientsRepository(Map<Long, Clients> entities, Validator<Clients> validator) {
        this.entities = entities;
        this.validator = validator;
    }

    public ClientsRepository() {
        this.entities = new HashMap<>();
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return a {@code Clients}  with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Clients findOne(Long id) {
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
    public Iterable<Clients> findAll() {
        Set<Clients> clients = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return clients;
    }

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Clients} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    @Override
    public Clients save(Clients entity) throws ValidatorException {
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
     * @return a {@code Clients} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Clients delete(Long id) {
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
     * @return a {@code Clients} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    @Override
    public Clients update(Clients entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return entities.computeIfPresent(entity.getIdEntity(), (k, v) -> entity);
        //throw new RuntimeException("not yet implemented");
    }

}
