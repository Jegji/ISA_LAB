package org.example;

import lombok.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Material> materials = initMaterials();

        printMaterials(materials);

        Set<Filament> allFilaments = materials.stream()
                .flatMap(material -> material.getFilamets().stream())
                .collect(Collectors.toSet());

        System.out.println("\nAll Filaments in Set:");
        allFilaments.stream()
                .forEach(filament -> {
                    System.out.println("\tBrand: " + filament.getBrand() +
                            ", Color: " + filament.getColor() +
                            ", Weight: " + filament.getWeight() +
                            ", Diameter: " + filament.getDiameter() +
                            ", Material: " + filament.getMaterial().getType() +
                            ", Melting temp: " + filament.getMaterial().getMeltingTemp()
                    );
                });

        System.out.println("\nFiltered and Sorted Filaments:");
        allFilaments.stream()
                .filter(filament -> filament.getDiameter() > 175)
                .sorted((c1, c2) -> c1.getBrand().compareTo(c2.getBrand()))
                .forEach(filament -> {
                    System.out.println("\tBrand: " + filament.getBrand() +
                            ", Color: " + filament.getColor() +
                            ", Weight: " + filament.getWeight() +
                            ", Diameter: " + filament.getDiameter() +
                            ", Material: " + filament.getMaterial().getType() +
                            ", Melting temp: " + filament.getMaterial().getMeltingTemp()
                    );
                });

        List<FilamentDto> characterDtos = allFilaments.stream()
                .map(filament -> FilamentDto.builder()
                        .brand(filament.getBrand())
                        .color(filament.getColor())
                        .weight(filament.getWeight())
                        .diameter(filament.getDiameter())
                        .material(filament.getMaterial().getType())
                        .build())
                .sorted()
                .collect(Collectors.toList());

        System.out.println("\nFilaments DTO (sorted by brand):");
        characterDtos.stream()
                .forEach(dto -> System.out.println("FilamentDto: Brand = " + dto.getBrand() +
                        ", Color = " + dto.getColor() +
                        ", Weight = " + dto.getWeight()+
                        ", Diameter = " + dto.getDiameter() +
                        ", Material = " + dto.getMaterial()
                ));

        serializeMaterials(materials, "materials.bin");

        List<Material> deserializedProf = deserializeMaterials("materials.bin");

        printMaterials(deserializedProf);

        System.out.println("\nThreads:");
        int poolSize = 4;
        ForkJoinPool ThreadPool = new ForkJoinPool(poolSize);

        ThreadPool.submit(() -> {
            materials.parallelStream().forEach(material -> {
                System.out.println("Type: " + material.getType() + ", Melting temp: " + material.getMeltingTemp());
                material.getFilamets().forEach(filament -> {
                    System.out.println("\tBrand: " + filament.getBrand() +
                            ", Color: " + filament.getColor() +
                            ", Weight: " + filament.getWeight()+
                            ", Diameter: " + filament.getDiameter());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            });
        });

        ThreadPool.shutdown();
        ThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }


    private static List<Material> initMaterials() {
        Material plaMaterial = Material.builder()
                .type("PLA")
                .meltingTemp(200)
                .build();

        Material absMaterial = Material.builder()
                .type("ABS")
                .meltingTemp(230)
                .build();

        Material petgMaterial = Material.builder()
                .type("PETG")
                .meltingTemp(240)
                .build();

        Material nylonMaterial = Material.builder()
                .type("Nylon")
                .meltingTemp(260)
                .build();

        Filament plaFilament1 = Filament.builder()
                .brand("BrandA")
                .color("Red")
                .weight(1000)
                .diameter(175)
                .material(plaMaterial)
                .build();

        Filament plaFilament2 = Filament.builder()
                .brand("BrandB")
                .color("Blue")
                .weight(750)
                .diameter(285)
                .material(plaMaterial)
                .build();

        Filament absFilament1 = Filament.builder()
                .brand("BrandC")
                .color("Black")
                .weight(1200)
                .diameter(175)
                .material(absMaterial)
                .build();

        Filament absFilament2 = Filament.builder()
                .brand("BrandD")
                .color("White")
                .weight(800)
                .diameter(285)
                .material(absMaterial)
                .build();

        Filament petgFilament1 = Filament.builder()
                .brand("BrandE")
                .color("Transparent")
                .weight(850)
                .diameter(175)
                .material(petgMaterial)
                .build();

        Filament petgFilament2 = Filament.builder()
                .brand("BrandF")
                .color("Green")
                .weight(900)
                .diameter(175)
                .material(petgMaterial)
                .build();

        Filament nylonFilament1 = Filament.builder()
                .brand("BrandG")
                .color("White")
                .weight(950)
                .diameter(175)
                .material(nylonMaterial)
                .build();

        Filament nylonFilament2 = Filament.builder()
                .brand("BrandH")
                .color("Yellow")
                .weight(1000)
                .diameter(175)
                .material(nylonMaterial)
                .build();

        plaMaterial.addFilament(plaFilament1);
        plaMaterial.addFilament(plaFilament2);

        absMaterial.addFilament(absFilament1);
        absMaterial.addFilament(absFilament2);

        petgMaterial.addFilament(petgFilament1);
        petgMaterial.addFilament(petgFilament2);

        nylonMaterial.addFilament(nylonFilament1);
        nylonMaterial.addFilament(nylonFilament2);

        List<Material> materials = new ArrayList<>();
        materials.add(plaMaterial);
        materials.add(absMaterial);
        materials.add(petgMaterial);
        materials.add(nylonMaterial);

        return materials;
    }
    private static void serializeMaterials(List<Material> materials, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(materials);
            System.out.println("\nSerialized to " + filename);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Material> deserializeMaterials(String filename) {
        List<Material> materials = null;
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            materials = (List<Material>) ois.readObject();
            System.out.println("Deserialized from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
        }
        return materials;
    }

    private static void printMaterials(List<Material> materials) {
        materials.forEach(Main::printMaterial);
    }

    private static void printMaterial(Material material){
        System.out.println("Type: " + material.getType() + ", Melting temp: " + material.getMeltingTemp());
        material.getFilamets().forEach(filament -> {
            System.out.println("\tBrand: " + filament.getBrand() +
                    ", Color: " + filament.getColor() +
                    ", Weight: " + filament.getWeight()+
                    ", Diameter: " + filament.getDiameter());
        });
    }
}
