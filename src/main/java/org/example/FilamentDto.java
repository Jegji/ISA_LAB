package org.example;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FilamentDto implements Comparable<FilamentDto>{
    private String brand;
    private String color;
    private int weight;
    private int diameter;
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
