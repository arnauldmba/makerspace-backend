package de.whs.makerspace.machine;

import de.whs.makerspace.location.Location;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "machines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String manufacturer;

    private String model;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private MachineType type;

    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
