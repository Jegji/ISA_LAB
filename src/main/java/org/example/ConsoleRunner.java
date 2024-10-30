package org.example;

import org.example.Filament;
import org.example.Material;
import org.example.service.FilamentService;
import org.example.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final FilamentService filamentService;
    private final MaterialService materialService;

    @Autowired
    public ConsoleRunner(FilamentService filamentService, MaterialService materialService) {
        this.filamentService = filamentService;
        this.materialService = materialService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;


        while (running) {
            displayMenu();
            String command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    listAllCategories();
                    break;
                case "2":
                    listAllElements();
                    break;
                case "3":
                    addNewElement(scanner);
                    break;
                case "4":
                    deleteElement(scanner);
                    break;
                case "5":
                    System.out.println("Stopping application...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\nAvailable Commands:");
        System.out.println("1. List all categories");
        System.out.println("2. List all elements");
        System.out.println("3. Add new element");
        System.out.println("4. Delete existing element");
        System.out.println("5. Stop application");
        System.out.print("Enter command: ");
    }

    private void listAllCategories() {
        List<Material> materials = materialService.getAllMaterials();
        if (materials.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            System.out.println("Available Categories:");
            materials.forEach(material -> System.out.println("- " + material.getType()));
        }
    }

    private void listAllElements() {
        List<Filament> filaments = filamentService.getAllFilaments();
        if (filaments.isEmpty()) {
            System.out.println("No elements available.");
        } else {
            System.out.println("Available Elements:");
            System.out.println(filaments.size());
            filaments.forEach(filament -> {
                System.out.println("- Brand: " + filament.getBrand() + ", Color: " + filament.getColor() + ", Type: " + filament.getMaterial().getType());
            });
        }
    }

    private void addNewElement(Scanner scanner) {
        System.out.print("Enter filament brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter filament color: ");
        String color = scanner.nextLine();
        System.out.print("Enter filament weight: ");
        int weight = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter filament diameter: ");
        int diameter = Integer.parseInt(scanner.nextLine());

        System.out.println("Select a material:");
        List<Material> materials = materialService.getAllMaterials();
        for (int i = 0; i < materials.size(); i++) {
            System.out.println((i + 1) + ". " + materials.get(i).getType());
        }
        int materialChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Material selectedMaterial = materials.get(materialChoice);

        Filament newFilament = Filament.builder()
                .id(UUID.randomUUID())
                .brand(brand)
                .color(color)
                .weight(weight)
                .diameter(diameter)
                .material(selectedMaterial)
                .build();

        filamentService.saveFilament(newFilament);
        System.out.println("New filament added successfully.");
    }

    private void deleteElement(Scanner scanner) {
        System.out.println("Select an element to delete:");
        List<Filament> filaments = filamentService.getAllFilaments();
        if (filaments.isEmpty()) {
            System.out.println("No elements available to delete.");
            return;
        }

        for (int i = 0; i < filaments.size(); i++) {
            System.out.println((i + 1) + ". " + filaments.get(i).getBrand() + " (" + filaments.get(i).getColor() + ")");
        }

        int filamentChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Filament selectedFilament = filaments.get(filamentChoice);

        filamentService.deleteFilament(selectedFilament.getId());
        System.out.println("Filament deleted successfully.");
    }

}
