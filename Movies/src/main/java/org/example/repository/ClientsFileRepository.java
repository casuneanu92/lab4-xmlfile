package org.example.repository;

import org.example.domain.Clients;
import org.example.domain.validators.Validator;
import org.example.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientsFileRepository extends InMemoryRepository<Long, Clients> {
    private String fileName;

    public ClientsFileRepository(Validator<Clients> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0).trim());
                String lastName = items.get(1).trim();
                String firstName = items.get((2)).trim();
                int age = Integer.parseInt(items.get(3).trim());

                Clients clients = new Clients(lastName, firstName, age);
                clients.setIdEntity(id);

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
                    entity.getIdEntity() + "," + entity.getLastName() + "," + entity.getFirstName() + "," + entity.getAge());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Clients> delete(Long id) throws ValidatorException, IllegalAccessException {
        Optional<Clients> optional = super.delete(id);
        deleteFromFile(id);
        if (optional.isPresent()) {
            return optional;
        }

        return Optional.empty();
    }

    private void deleteFromFile(Long id) {
        Path path = Paths.get(fileName);
        String lineContent = id + ", ";
        try {
            File file = new File(String.valueOf(path));
            List<String> out =Files.lines(file.toPath()).
                    filter(line -> !line.startsWith(lineContent)).
                    collect(Collectors.toList());
            Files.write(file.toPath(),out,StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Clients> update(Clients entity) throws ValidatorException, IllegalAccessException {
        Optional<Clients> optional = super.update(entity);
        delete(entity.getIdEntity());
        save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }

}