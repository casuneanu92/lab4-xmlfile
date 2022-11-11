package org.example.repository;

import org.example.domain.Clients;
import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientsFileRepository extends InMemoryRepository<Long, Clients> {
    private String fileName;

    public ClientsFileRepository (Validator<Clients> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String cnp = items.get(1);
                String firstName = items.get((2));
                String lastName = items.get((3));
                int age = Integer.parseInt(items.get(4));

                Clients clients = new Clients(cnp, firstName, lastName, age);
                clients.setId(id);

                try {
                    super.save(clients);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Clients> save(Clients entity) throws ValidatorException {
        Optional<Clients> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Clients entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getCnp() + "," + entity.getFirstName() + ", " + entity.getLastName() + "," + entity.getAge());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
