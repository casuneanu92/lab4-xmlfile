package org.example;

import org.example.domain.Clients;
import org.example.domain.Movies;
import org.example.domain.validators.ClientsValidator;
import org.example.domain.validators.MoviesValidator;
import org.example.domain.validators.Validator;
import org.example.repository.Repository;
import org.example.service.ClientsService;
import org.example.service.MoviesService;
import org.example.ui.ConsolaFile;
import org.example.ui.Console;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;


public class Main {
    private static String clientsPropertyXML = null;
    private static String moviesPropertyXML = null;
    private static String clientsPropertyFile = null;
    private static String moviesPropertyFile = null;
    private static String clientsPropertyDB = null;
    private static String moviesPropertyDB = null;

    private static final String URL = "jdbc:postgresql://localhost:5432/hospital";
    private static String USER = null;
    private static String PASSWORD = null;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Validator<Clients> clientsValidator = new ClientsValidator();
        Validator<Movies> moviesValidator = new MoviesValidator();


        try (InputStream input = new FileInputStream("Movies/src/main/resources/repository-config.properties.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            clientsPropertyFile = properties.getProperty("clientsRepositoryFile");
            moviesPropertyFile = properties.getProperty("moviesRepositoryFile");
            clientsPropertyXML = properties.getProperty("clientsRepositoryXML");
            moviesPropertyXML = properties.getProperty("moviesRepositoryXML");
            clientsPropertyDB = properties.getProperty("clientsRepositoryDB");
            moviesPropertyDB = properties.getProperty("moviesRepositoryDB");
            USER = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");



        }
        catch(IOException ex){
            System.out.println("Nu a preluat proprietalile");
        }
        System.out.println(clientsPropertyFile);
        System.out.println(moviesPropertyFile);
        System.out.println(clientsPropertyXML);
        System.out.println(moviesPropertyXML);
        System.out.println(clientsPropertyDB);
        System.out.println(moviesPropertyDB);
        System.out.println(USER);
        System.out.println(PASSWORD);



        Repository<Long,Clients> clientsRepository= null;
        Repository<Long, Movies> moviesRepository = null;
        //alegere optiune fisier
        ConsolaFile consoleFile = new ConsolaFile();
        int optiune = consoleFile.showMenu();
        System.out.println(optiune);
        if(optiune==1) {
            Class<?> clientsRepoTypeClass = Class.forName(clientsPropertyFile);
            if (clientsPropertyFile.equals("org.example.repository.ClientsFileRepository")) {
                Constructor clientsConstructor = clientsRepoTypeClass.getDeclaredConstructor(Validator.class, String.class);
                clientsRepository = (Repository<Long, Clients>) clientsConstructor.newInstance(clientsValidator, "Movies/data/clients.txt");
            }
            Class<?> moviesRepoTypeClass = Class.forName(moviesPropertyFile);
            if (moviesPropertyFile.equals("org.example.repository.MoviesFileRepository")) {
                Constructor moviesConstructor = moviesRepoTypeClass.getDeclaredConstructor(Validator.class, String.class);
                moviesRepository = (Repository<Long, Movies>) moviesConstructor.newInstance(moviesValidator, "Movies/data/movies.txt");
            }
        }
        else if(optiune==2) {
            Class<?> clientsRepoTypeClass = Class.forName(clientsPropertyXML);
            if (clientsPropertyXML.equals("org.example.repository.ClientsXMLRepository")) {
                Constructor moviesConstructor = clientsRepoTypeClass.getDeclaredConstructor(Validator.class, String.class);
                clientsRepository = (Repository<Long, Clients>) moviesConstructor.newInstance(clientsValidator, "Movies/data/clients.xml");
            }
            Class<?> moviesRepoTypeClass = Class.forName(moviesPropertyXML);
            if (moviesPropertyXML.equals("org.example.repository.MoviesXMLRepository")) {
                Constructor clientsConstructor = moviesRepoTypeClass.getDeclaredConstructor(Validator.class, String.class);
                moviesRepository = (Repository<Long, Movies>) clientsConstructor.newInstance(moviesValidator, "Movies/data/movies.xml");
            }
        }
        else if(optiune==3) {
            Class<?> clientsRepoTypeClass = Class.forName(clientsPropertyDB);
            if (clientsPropertyDB.equals("org.example.repository.ClientsDBRepository")) {
                Constructor clientsConstructor = clientsRepoTypeClass.getDeclaredConstructor(Validator.class, String.class, String.class, String.class);
                clientsRepository = (Repository<Long, Clients>) clientsConstructor.newInstance(clientsValidator, URL,USER,PASSWORD);
            }
            Class<?> moviesRepoTypeClass = Class.forName(moviesPropertyDB);
            if (moviesPropertyDB.equals("org.example.repository.MoviesDBRepository")) {
                Constructor moviesConstructor = moviesRepoTypeClass.getDeclaredConstructor(Validator.class, String.class, String.class, String.class);
                moviesRepository = (Repository<Long, Movies>) moviesConstructor.newInstance(moviesValidator, URL, USER, PASSWORD);
            }
        }

        ClientsService clientsService = new ClientsService(clientsRepository);
        MoviesService moviesService = new MoviesService(moviesRepository);

        Console console = new Console(clientsService, moviesService);
        console.startConsole();
    }
}