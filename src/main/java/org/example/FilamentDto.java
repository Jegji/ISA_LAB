package org.example;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filament_dtos")
public class FilamentDto implements Serializable, Comparable<FilamentDto> {

    @Id
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "diameter", nullable = false)
    private int diameter;

    @Column(name = "material", nullable = false)
    private String material;

    @Override
    public int compareTo(FilamentDto other) {
        int brandComparison = this.brand.compareTo(other.brand);
        if (brandComparison != 0) {
            return brandComparison;
        }

        int materialComparison = this.material.compareTo(other.material);
        if (materialComparison != 0) {
            return materialComparison;
        }

        int weightComparison = Integer.compare(this.weight, other.weight);
        if (weightComparison != 0) {
            return weightComparison;
        }

        int lengthComparison = Integer.compare(this.diameter, other.diameter);
        if (lengthComparison != 0) {
            return lengthComparison;
        }

        return this.color.compareTo(other.color);
    }
}
