package org.example.controller;

import org.example.dto.FilamentCreateDto;
import org.example.dto.FilamentReadDto;
import org.example.dto.FilamentCollectionDto;
import org.example.dto.MaterialReadDto;
import org.example.service.FilamentService;
import org.example.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filaments")
public class FilamentController {

    private final FilamentService filamentService;
    private final MaterialService materialService;

    @Autowired
    public FilamentController(FilamentService filamentService, MaterialService materialService) {
        this.filamentService = filamentService;
        this.materialService = materialService;
    }
    @GetMapping
    public ResponseEntity<List<FilamentReadDto>> getAllFilaments() {
        List<FilamentReadDto> filaments = filamentService.getAllFilaments();
        return ResponseEntity.ok(filaments);
    }

    @GetMapping("/{filamentId}")
    public ResponseEntity<FilamentReadDto> getMaterialById(@PathVariable UUID filamentId) {
        try {
            FilamentReadDto filamentReadDto = filamentService.getFilamentById(filamentId);
            return ResponseEntity.ok(filamentReadDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/{materialId}")
    public ResponseEntity<FilamentReadDto> createFilament(
            @PathVariable UUID materialId,
            @RequestBody FilamentCreateDto filamentCreateDto) {
        try {
            filamentService.createFilament(materialId, filamentCreateDto);
            return ResponseEntity.status(201).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{filamentId}")
    public ResponseEntity<Void> deleteFilament(@PathVariable UUID filamentId) {
        if (filamentService.deleteFilament(filamentId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<FilamentReadDto> updateFilament(
            @PathVariable UUID materialId,
            @RequestBody FilamentCreateDto filamentCreateDto) {
        try {
            filamentService.updateFilament(materialId, filamentCreateDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).build();
        }
    }
}
