package org.example.service;

import org.example.Filament;
import org.example.repository.FilamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FilamentService {
    private final FilamentRepository filamentRepository;
    @Autowired
    public FilamentService(FilamentRepository filamentRepository) {
        this.filamentRepository = filamentRepository;
    }
    public List<Filament> getFilamentsByMaterialType(String type) {
        return filamentRepository.findByMaterial_Type(type);
    }
    public Filament saveFilament(Filament filament) {
        return filamentRepository.save(filament);
    }
    public Filament getFilamentById(UUID id) {
        return filamentRepository.findById(id).orElse(null);
    }
    public void deleteFilament(UUID id) {
        filamentRepository.deleteById(id);
    }
    public List<Filament> getAllFilaments() {
        return filamentRepository.findAll();
    }
}
