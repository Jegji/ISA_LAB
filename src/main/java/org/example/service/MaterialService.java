package org.example.service;

import org.example.Material;
import org.example.Filament;
import org.example.dto.FilamentCreateDto;
import org.example.dto.MaterialCreateDto;
import org.example.dto.MaterialReadDto;
import org.example.repository.MaterialRepository;
import org.example.repository.FilamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final FilamentRepository filamentRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository, FilamentRepository filamentRepository) {
        this.materialRepository = materialRepository;
        this.filamentRepository = filamentRepository;
    }

    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material createMaterial(MaterialCreateDto materialCreateDto) {
        Material material = new Material();
        material.setId(UUID.randomUUID());
        material.setType(materialCreateDto.getType());
        material.setMeltingTemp(materialCreateDto.getMeltingTemp());
        return materialRepository.save(material);
    }

    public Material updateMaterial(UUID id, MaterialCreateDto materialCreateDto) {
        Optional<Material> materialOptional = materialRepository.findById(id);
        if (materialOptional.isPresent()) {
            Material material = materialOptional.get();
            material.setType(materialCreateDto.getType());
            material.setMeltingTemp(materialCreateDto.getMeltingTemp());
            return materialRepository.save(material);
        } else {
            throw new RuntimeException("Material not found with id: " + id);
        }
    }

    public MaterialReadDto getMaterialById(UUID id) {
        Material foundMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found with id " + id));
        return MaterialReadDto.from(foundMaterial);
    }

    public boolean deleteMaterial(UUID id) {
        if (materialRepository.existsById(id)) {
            materialRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<MaterialReadDto> getAllMaterials() {
        List<Material> materials = materialRepository.findAll();
        return materials.stream()
                .map(MaterialReadDto::from)
                .collect(Collectors.toList());
    }

    public boolean materialExists(UUID materialId) {
        return materialRepository.existsById(materialId);
    }

}