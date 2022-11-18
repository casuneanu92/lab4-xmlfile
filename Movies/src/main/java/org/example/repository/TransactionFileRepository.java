package org.example.repository;

import org.example.domain.Transaction;
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

public class TransactionFileRepository extends InMemoryRepository<Long, Transaction> {
    private String fileName;

    public TransactionFileRepository(Validator<Transaction> validator, String fileName) {
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
                Long idClients = Long.valueOf(items.get(1).trim());
                Long idMovies = Long.valueOf(items.get(2).trim());
                Long price = Long.valueOf(items.get(3).trim());


                Transaction transaction = new Transaction(idClients, idMovies, price);
                transaction.setIdEntity(id);

                try {
                    super.save(transaction);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Transaction entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getIdEntity() + "," + entity.getIdClients() + "," + entity.getIdMovies() + "," + entity.getPrice());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFromFile(Long id) {
        Path path = Paths.get(fileName);
        String lineContent = id + ",";
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
    public Optional<Transaction> update(Transaction entity) throws ValidatorException, IllegalAccessException {
        Optional<Transaction> optional = super.update(entity);
        if (optional.isPresent()) {
            return optional;
        }
        updateFile(entity);
        return Optional.empty();
    }

    private void updateFile(Transaction entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            bufferedWriter.write(
                    entity.getIdEntity() + ", " + entity.getIdClients() + ", " + entity.getIdMovies() + ", " + entity.getPrice());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
