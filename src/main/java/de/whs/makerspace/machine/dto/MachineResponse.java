package de.whs.makerspace.machine.dto;

import de.whs.makerspace.machine.MachineStatus;
import de.whs.makerspace.machine.MachineType;

public record MachineResponse(
        Long id,
        String name,
        String manufacturer,
        String model,
        String description,
        MachineType type,
        MachineStatus status,
        String imageUrl,
        Long locationId,
        String city,
        String roomName,
        String building,
        String floor
) {
}