package org.example;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Material implements Serializable {
    private String type;
    private int meltingTemp;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Filament> filamets = new ArrayList<>();

    public void addFilament(Filament filament){
        this.filamets.add(filament);
        filament.setMaterial(this);
    }

}
