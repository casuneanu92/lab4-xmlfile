package org.example.service;
import org.example.domain.Clients;
import org.example.domain.validators.ValidatorException;
import org.example.repository.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
public class ClientsService {
    private Repository<Long, Clients> repository;
    public ClientsService(Repository<Long, Clients> repository) {
        this.repository = repository;
    }

    public void addClients(Clients clients) throws ValidatorException {
        repository.save(clients);
    }

    public Set<Clients> getAllCLients() {
        Iterable<Clients> clients = repository.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }


    public void deleteClients(long id) {
        repository.delete(id);
    }
}