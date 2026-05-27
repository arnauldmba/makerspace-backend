package de.whs.makerspace.machine.dto;

import de.whs.makerspace.machine.MachineStatus;
import de.whs.makerspace.machine.MachineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MachineRequest(

        @NotBlank(message = "Name is required")
        String name,

        String manufacturer,

        String model,

        String description,

        @NotNull(message = "Type is required")
        MachineType type,

        @NotNull(message = "Status is required")
        MachineStatus status,

        String imageUrl,

        @NotNull(message = "Location id is required")
        Long locationId
) {
}
