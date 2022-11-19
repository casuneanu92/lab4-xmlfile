package org.example.repository;

import org.example.domain.Transaction;
import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionRepository implements ITransactionRepository{
    private Map<Long, Transaction> entities;
    private Validator<Transaction> validator;

    public TransactionRepository(Map<Long, Transaction> entities, Validator<Transaction> validator) {
        this.entities = entities;
        this.validator = validator;
    }

    public TransactionRepository() {
        this.entities = new HashMap<>();
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return a {@code Transaction}  with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Transaction findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return entities.get(id);
    }

    /**
     * @return all entities.
     */
    @Override
    public Iterable<Transaction> findAll() {
        Set<Transaction> transactions = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return transactions;
    }

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Transaction} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    @Override
    public Transaction save(Transaction entity) throws ValidatorException {
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
     * @return a {@code Transaction} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Transaction delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return entities.remove(id);
    }

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return a {@code Transaction} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     */
    @Override
    public Transaction update(Transaction entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return entities.computeIfPresent(entity.getIdEntity(), (k, v) -> entity);
    }

}
