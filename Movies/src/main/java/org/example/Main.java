package org.example;

import org.example.domain.Clients;
import org.example.domain.Movies;
import org.example.domain.validators.ClientsValidator;
import org.example.domain.validators.MoviesValidator;
import org.example.domain.validators.Validator;
import org.example.repository.Repository;
import org.example.service.ClientsService;
import org.example.service.MoviesService;
import org.example.ui.Console;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;
import org.example.repository.ClientsXMLRepository;


public class Main {
    //    public static void main(String[] args) {
//
//        Validator<Clients> clientsValidator = new ClientsValidator();
//        Validator<Movies> moviesValidator = new MoviesValidator();
//
//        Repository<Long, Clients> clientsRepository = new ClientsFileRepository(clientsValidator, "./data/clients");
//        Repository<Long, Movies> moviesRepository = new MoviesFileRepository(moviesValidator, "./data/movies");
//
//        ClientsService clientsService=new ClientsService(clientsRepository);
//        MoviesService moviesService = new MoviesService(moviesRepository);
//        Console console=new Console(clientsService, moviesService);
//
//        console.startConsole();
//
//        System.out.println("Bye");
//    }
//}


    private static String moviesProperty = null;
    private static String clientsProperty = null;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Validator<Movies> moviesValidator = new MoviesValidator();
        Validator<Clients> clientsValidator = new ClientsValidator();


        try (InputStream input = new FileInputStream("src/main/resources/repository-config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            moviesProperty = properties.getProperty("configMoviesRepository");
            clientsProperty = properties.getProperty("configClientsRepository");


            System.out.println(moviesProperty);
            System.out.println(clientsProperty);

        } catch (IOException ex) {
            System.out.println("Nu a preluat proprietatile");
        }

        Repository<Long, Movies> moviesRepository = null;
        Repository<Long, Clients> clientsRepository = null;

        Class<?> moviesRepoTypeClass = Class.forName(moviesProperty);
        if (moviesProperty.equals("repository.MoviesXMLRepository")) {
            Constructor moviesConstructor = moviesRepoTypeClass.getDeclaredConstructor(Validator.class, String.class);
            moviesRepository = (Repository<Long, Movies>) moviesConstructor.newInstance(moviesValidator, "./data/movies.xml");
        } else {
            Constructor moviesConstructor = moviesRepoTypeClass.getDeclaredConstructor(Validator.class);
            moviesRepository = (Repository<Long, Movies>) moviesConstructor.newInstance(moviesValidator);
        }

        Class<?> clientsRepoTypeClass = Class.forName(clientsProperty);
        if (clientsProperty.equals("repository.ClientsXMLRepository")) {
            Constructor clientsConstructor = clientsRepoTypeClass.getDeclaredConstructor(Validator.class, String.class);
            clientsRepository = (Repository<Long, Clients>) clientsConstructor.newInstance(clientsValidator, "data/clients.xml");
        } else {
            Constructor clientsConstructor = moviesRepoTypeClass.getDeclaredConstructor(Validator.class);
            clientsRepository = (Repository<Long, Clients>) clientsConstructor.newInstance(moviesValidator);
        }

        MoviesService moviesService = new MoviesService(moviesRepository);
        ClientsService clientsService = new ClientsService(clientsRepository);
        /*Repository<Long, Doctor> doctorRepository1 = new InMemoryRepository<Long, Doctor>(doctorValidator);
        DoctorService doctorService = new DoctorService(doctorRepository1);
        Repository<Long, Patient> patientRepository1 = new InMemoryRepository<Long, Patient>(patientValidator);
        PatientService patientService = new PatientService(patientRepository1);*/
        Console console = new Console(clientsService, moviesService);
        console.startConsole();
    }
}

