package org.example;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Filament implements Serializable {
    private String brand;
    private String color;
    private int weight;
    private int diameter;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Material material;
}
