package org.example;

import org.example.domain.Clients;
import org.example.domain.Movies;
import org.example.domain.validators.ClientsValidator;
import org.example.domain.validators.MoviesValidator;
import org.example.domain.validators.Validator;
import org.example.repository.ClientsFileRepository;
import org.example.repository.InMemoryRepository;
import org.example.repository.MoviesFileRepository;
import org.example.repository.Repository;
import org.example.service.ClientsService;
import org.example.service.MoviesService;
import org.example.ui.Console;


public class Main {
    public static void main(String[] args) {

        Validator<Clients> clientsValidator = new ClientsValidator();
        Validator<Movies> moviesValidator = new MoviesValidator();

        Repository<Long, Clients> clientsRepository = new ClientsFileRepository(clientsValidator, "./data/clients");
        Repository<Long, Movies> moviesRepository = new MoviesFileRepository(moviesValidator, "./data/movies");

        ClientsService clientsService=new ClientsService(clientsRepository);
        MoviesService moviesService = new MoviesService(moviesRepository);
        Console console=new Console(clientsService, moviesService);

        console.startConsole();

        System.out.println("Hello");
    }
}