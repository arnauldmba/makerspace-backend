package de.whs.makerspace.machine;

import de.whs.makerspace.location.Location;
import de.whs.makerspace.machine.dto.MachineRequest;
import de.whs.makerspace.machine.dto.MachineResponse;

public class MachineMapper {

    public static MachineResponse toResponse(Machine machine) {
        Location location = machine.getLocation();

        return new MachineResponse(
                machine.getId(),
                machine.getName(),
                machine.getManufacturer(),
                machine.getModel(),
                machine.getDescription(),
                machine.getType(),
                machine.getStatus(),
                machine.getImageUrl(),
                location != null ? location.getId() : null,
                location != null ? location.getCity() : null,
                location != null ? location.getRoomName() : null,
                location != null ? location.getBuilding() : null,
                location != null ? location.getFloor() : null
        );
    }

    public static Machine toEntity(MachineRequest request, Location location) {
        Machine machine = new Machine();
        machine.setName(request.name());
        machine.setManufacturer(request.manufacturer());
        machine.setModel(request.model());
        machine.setDescription(request.description());
        machine.setType(request.type());
        machine.setStatus(request.status());
        machine.setImageUrl(request.imageUrl());
        machine.setLocation(location);
        return machine;
    }

    public static void updateEntity(Machine machine, MachineRequest request, Location location) {
        machine.setName(request.name());
        machine.setType(request.type());
        machine.setStatus(request.status());
        machine.setImageUrl(request.imageUrl());
        machine.setLocation(location);
    }
}