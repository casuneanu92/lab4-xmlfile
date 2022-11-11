package org.example.ui;

import org.example.domain.Clients;
import org.example.domain.Movies;
import org.example.service.ClientsService;
import org.example.service.MoviesService;

import java.util.Scanner;
import java.util.Set;

public class Console {
    private ClientsService clientsService;
    private MoviesService moviesService;
    public Console(ClientsService clientsService, MoviesService moviesService) {
        this.clientsService = clientsService;
        this.moviesService = moviesService;
    }

    private Scanner scanner = new Scanner(System.in);
    private void showMenu() {
        System.out.println("1. Adaugare clienti");
        System.out.println("2. Afisare clienti");
        System.out.println("3. Delete clienti");
        System.out.println("4. Adaugare movies");
        System.out.println("5. Afisare movies");
        System.out.println("6. Update movies");
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
                this.deleteClients();
            } else if (option.equals("4")) {
                this.addMovies();
            } else if (option.equals("5")){
                this.handleShowAllMovies();
            } else if (option.equals("6")) {
                this.updateMovies();
            } else if (option.equals("x")) {
                break;
            } else {
                System.out.println("Comanda invalida!");
            }
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


    private void addClients() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("id = ");
        Long id = scanner.nextLong();
        System.out.println("cnp = ");
        String cnp = scanner.next();
        System.out.println("firstName = ");
        String firstName = scanner.next();
        System.out.println("lastName = ");
        String lastName = scanner.next();
        System.out.println("age = ");
        int age = scanner.nextInt();
        Clients clients = new Clients(id, cnp, firstName, lastName, age);
        clientsService.addClients(clients);
    }

    private void handleShowAllClients() {
        for (Clients clients : this.clientsService.getAllCLients()) {
            System.out.println(clients.toString());
        }
    }

    private void addMovies() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("id = ");
        Long id = scanner.nextLong();
        System.out.println("name = ");
        String name = scanner.next();
        System.out.println("year = ");
        int year = scanner.nextInt();
        System.out.println("duration = ");
        int duration = scanner.nextInt();
        Movies movies = new Movies(id, name, year, duration);
        moviesService.addMovies(movies);
    }

    private void handleShowAllMovies() {
        for (Movies movies : this.moviesService.getAllMovies()) {
            System.out.println(movies.toString());
        }
    }
    private void updateMovies() {
        try {
            System.out.println("Dati ID-ul: ");
            long id = this.scanner.nextLong();
            System.out.println("Dati name: ");
            String name = this.scanner.next();
            System.out.println("Dati year: ");
            int year = this.scanner.nextInt();
            System.out.println("Dati duration: ");
            int duration = this.scanner.nextInt();
            Movies movies = new Movies(id, name, year, duration);
            this.moviesService.updateMovies(movies);
            System.out.println("Update efectuat cu succes!");
        } catch (Exception exception) {
            System.out.println("Au aparut erorile:");
            System.out.println(exception.getMessage());
        }
    }
}