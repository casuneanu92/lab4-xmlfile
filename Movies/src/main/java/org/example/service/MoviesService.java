package org.example.service;
import org.example.domain.Movies;
import org.example.domain.validators.ValidatorException;
import org.example.repository.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
public class MoviesService {
    private Repository<Long, Movies> repository;
    public MoviesService(Repository<Long, Movies> repository) {
        this.repository = repository;
    }

    public void addMovies(Movies movies) throws ValidatorException {
        repository.save(movies);
    }

    public Set<Movies> getAllMovies() {
        Iterable<Movies> movies = repository.findAll();
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
    }


    public  void updateMovies(Movies movies) throws ValidatorException {
        repository.update(movies);
    }
}