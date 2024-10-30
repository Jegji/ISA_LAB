package org.example;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materials")
public class Material implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "melting_temp", nullable = false)
    private int meltingTemp;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Filament> filamets = new ArrayList<>();

    public Material(String type, int meltingTemp) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.meltingTemp = meltingTemp;
    }
    public void addFilament(Filament filament){
        this.filamets.add(filament);
        filament.setMaterial(this);
    }

}
