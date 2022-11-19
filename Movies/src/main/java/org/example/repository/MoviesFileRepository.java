package org.example.repository;

import org.example.domain.Movies;
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

public class MoviesFileRepository extends InMemoryRepository<Long, Movies> {
    private String fileName;

    public MoviesFileRepository(Validator<Movies> validator, String fileName) {
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
                String name = items.get(1).trim();
                int year = Integer.parseInt(items.get(2).trim());
                int duration = Integer.parseInt(items.get(3).trim());

                Movies movies = new Movies(name, year, duration);
                movies.setIdEntity(id);

                try {
                    super.save(movies);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Movies> save(Movies entity) throws ValidatorException {
        Optional<Movies> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Movies entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getIdEntity() + "," + entity.getName() + "," + entity.getYear() + "," + entity.getDuration());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Movies> delete(Long id) throws ValidatorException, IllegalAccessException {
        Optional<Movies> optional = super.delete(id);
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
            List<String> out = Files.lines(file.toPath()).
                    filter(line -> !line.startsWith(lineContent)).
                    collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Movies> update(Movies entity) throws ValidatorException, IllegalAccessException {
        Optional<Movies> optional = super.update(entity);
        delete(entity.getIdEntity());
        save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }
}
