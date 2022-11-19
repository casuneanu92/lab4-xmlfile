package org.example.ui;

import org.example.domain.Clients;
import org.example.domain.Movies;
import org.example.service.ClientsService;
import org.example.service.MoviesService;

import java.util.Scanner;

public class Console {
    private ClientsService clientsService;
    private MoviesService moviesService;


    private Scanner scanner = new Scanner(System.in);


    public Console(ClientsService clientsService, MoviesService moviesService) {
        this.clientsService = clientsService;
        this.moviesService = moviesService;

    }


    private void showMenu() {
        System.out.println("1. Adaugare clienti");
        System.out.println("2. Afisare clienti");
        System.out.println("3. Adaugare filme");
        System.out.println("4. Afisare filme");
        System.out.println("5. Update clienti");
        System.out.println("6. Delete clienti");
        System.out.println("7. Update filme");
        System.out.println("8. Delete filme");
        System.out.println("x. Iesire");
    }
    public void startConsole() {
        while (true) {
            this.showMenu();

            System.out.println("Alegeti o optiune:");
            String option = scanner.next();

            if (option.equals("1")) {
                this.addClients();
            } else if (option.equals("2")){
                this.handleShowAllClients();
            } else if (option.equals("3")) {
                this.addMovies();
            } else if (option.equals("4")) {
                this.handleShowAllMovies();
            } else if (option.equals("5")) {
                this.updateClients();
            } else if (option.equals("6")) {
                this.deleteClients();
            } else if (option.equals("7")) {
                this.updateMovies();
            } else if (option.equals("8")) {
                this.deleteMovies();
            } else if (option.equals("x")) {
                break;
            } else {
                System.out.println("Comanda invalida!");
            }
        }
    }

    private void deleteMovies() {
        try {
            System.out.println("Dati id-ul: ");
            long id = this.scanner.nextLong();
            moviesService.deleteMovies(id);
        }
        catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }

    private void updateMovies() {
        try {
            System.out.println("Dati ID-ul: ");
            long id = this.scanner.nextLong();
            System.out.println("Dati numele: ");
            String name = this.scanner.next();
            System.out.println("Dati anul: ");
            int year = this.scanner.nextInt();
            System.out.println("Dati durata: ");
            int duration = this.scanner.nextInt();
            Movies movies = new Movies(id, name, year, duration);
            this.moviesService.updateMovies(movies);
            System.out.println("Update efectuat cu succes!");
        } catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }

    private void deleteClients() {
        try {
            System.out.println("Dati id-ul: ");
            long id = this.scanner.nextLong();
            clientsService.deleteClients(id);
        }
        catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }

    private void updateClients() {
        try {
            System.out.println("Dati ID-ul: ");
            long id = this.scanner.nextLong();
            System.out.println("Dati numele: ");
            String lastName = this.scanner.next();
            System.out.println("Dati prenumele: ");
            String firstName = this.scanner.next();
            System.out.println("Dati varsta: ");
            int age = this.scanner.nextInt();
            Clients clients = new Clients(id, lastName, firstName, age);
            this.clientsService.updateClients(clients);
            System.out.println(" Update efectuat cu succes!");
        } catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }


    private void addClients() {
        try {
            System.out.println("Dati ID-ul: ");
            long id = this.scanner.nextLong();
            System.out.println("Dati numele: ");
            String lastName = this.scanner.next();
            System.out.println("Dati prenumele: ");
            String firstName = this.scanner.next();
            System.out.println("Dati varsta: ");
            int age = this.scanner.nextInt();
            Clients clients = new Clients(id, lastName, firstName, age);
            this.clientsService.addClients(clients);
            System.out.println("Adaugare efectuata cu succes!");
        } catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }
    private void handleShowAllClients() {
        for (Clients clients : this.clientsService.getAllClients()) {
            System.out.println(clients.toString());
        }
    }
    private void addMovies() {
        try {
            System.out.println("Dati ID-ul: ");
            long id = this.scanner.nextLong();
            System.out.println("Dati numele: ");
            String name = this.scanner.next();
            System.out.println("Dati anul: ");
            int year = this.scanner.nextInt();
            System.out.println("Dati durata: ");
            int duration = this.scanner.nextInt();
            Movies movies = new Movies(id, name, year, duration);
            this.moviesService.addMovies(movies);
            System.out.println("Adaugare efectuata cu succes!");
        } catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }
    private void handleShowAllMovies() {
        for (Movies movies : this.moviesService.getAllMovies()) {
            System.out.println(movies.toString());
        }
    }
}