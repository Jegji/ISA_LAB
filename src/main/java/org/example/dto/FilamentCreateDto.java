package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilamentCreateDto {
    private String brand;
    private String color;
    private int weight;
    private int diameter;
    private UUID materialId;
}
