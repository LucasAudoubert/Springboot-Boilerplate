package com.example.demo.model;

import java.util.Scanner;

public class OldMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TechInterface pc = new PC("Custom Build", 32, "RTX 3050 Ti");
        TechInterface laptop = new Laptop("Dell XPS", 16, 10);
        TechInterface phone = new Phone("Samsung", 8, "5G");

        TechInterface[] devices = { pc, laptop, phone };

        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU DE CONTROLE ---");
            System.out.println("1. Demarrer le PC");
            System.out.println("2. Demarrer le Laptop");
            System.out.println("3. Demarrer le Telephone");
            System.out.println("4. Demarrer tous les appareils");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        pc.demarrer();
                        break;
                    case 2:
                        laptop.demarrer();
                        break;
                    case 3:
                        phone.demarrer();
                        break;
                    case 4:
                        System.out.println("\n--- Lancement de tous les equipements ---");
                        for (TechInterface device : devices) {
                            device.demarrer();
                        }
                        break;
                    case 0:
                        running = false;
                        System.out.println("Extinction du programme.");
                        break;
                    default:
                        System.out.println("Choix invalide, reessaie.");
                }
            } else {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next();
            }
        }

        scanner.close();
    }
}
