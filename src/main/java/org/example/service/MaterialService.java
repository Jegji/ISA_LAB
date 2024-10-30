package org.example.service;

import org.example.Material;
import org.example.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }
    public Material getMaterialById(UUID id) {
        return materialRepository.findById(id).orElse(null);
    }
    public void deleteMaterial(UUID id) {
        materialRepository.deleteById(id);
    }
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
}
