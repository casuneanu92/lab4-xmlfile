package org.example.ui;

import java.util.Scanner;

public class ConsolaFile {

    private Scanner scanner = new Scanner(System.in);
    public int showMenu() {
        System.out.println("1. Pentru File");
        System.out.println("2. Pentru XML");
        System.out.println("3. Pentru DB");
        System.out.println("Alegeti optiunea: ");
        int optiune = scanner.nextInt();
        return optiune;
    }
}
