package org.example;

import jakarta.annotation.PostConstruct;
import org.example.service.FilamentService;
import org.example.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer{

    private final FilamentService filamentService;
    private final MaterialService materialService;

    @Autowired
    public DataInitializer(FilamentService filamentService, MaterialService materialService) {
        this.filamentService = filamentService;
        this.materialService = materialService;
    }

    @PostConstruct
    public void init() {
        List<Material> materials = initMaterials();

        for (Material material : materials) {
            Material savedMaterial = materialService.saveMaterial(material);
            System.out.println("Saved material: " + savedMaterial);

            for (Filament filament : material.getFilamets()) {
                Filament savedFilament = filamentService.saveFilament(filament);
                System.out.println("Saved filament: " + savedFilament);
            }
        }

        System.out.println("Example data initialized successfully!");
    }

    private List<Material> initMaterials() {
        Material plaMaterial = Material.builder()
                .id(UUID.randomUUID())
                .type("PLA")
                .meltingTemp(200)
                .build();

        Material absMaterial = Material.builder()
                .id(UUID.randomUUID())
                .type("ABS")
                .meltingTemp(230)
                .build();

        Material petgMaterial = Material.builder()
                .id(UUID.randomUUID())
                .type("PETG")
                .meltingTemp(240)
                .build();

        Material nylonMaterial = Material.builder()
                .id(UUID.randomUUID())
                .type("Nylon")
                .meltingTemp(260)
                .build();

        Filament plaFilament1 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandA")
                .color("Red")
                .weight(1000)
                .diameter(175)
                .material(plaMaterial)
                .build();

        Filament plaFilament2 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandB")
                .color("Blue")
                .weight(750)
                .diameter(285)
                .material(plaMaterial)
                .build();

        Filament absFilament1 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandC")
                .color("Black")
                .weight(1200)
                .diameter(175)
                .material(absMaterial)
                .build();

        Filament absFilament2 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandD")
                .color("White")
                .weight(800)
                .diameter(285)
                .material(absMaterial)
                .build();

        Filament petgFilament1 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandE")
                .color("Transparent")
                .weight(850)
                .diameter(175)
                .material(petgMaterial)
                .build();

        Filament petgFilament2 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandF")
                .color("Green")
                .weight(900)
                .diameter(175)
                .material(petgMaterial)
                .build();

        Filament nylonFilament1 = Filament.builder()
                .id(UUID.randomUUID())
                .brand("BrandG")
                .color("White")
                .weight(950)
                .diameter(175)
                .material(nylonMaterial)
                .build();

        Filament nylonFilament2 = Filament.builder()
                .id(UUID.randomUUID())
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

        return List.of(plaMaterial, absMaterial, petgMaterial, nylonMaterial);
    }
}
