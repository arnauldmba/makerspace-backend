package de.whs.makerspace.location;

import de.whs.makerspace.location.dto.LocationRequest;
import de.whs.makerspace.location.dto.LocationResponse;

public class LocationMapper {

    public static LocationResponse toResponse(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getCity(),
                location.getRoomName(),
                location.getBuilding(),
                location.getFloor()
        );
    }

    public static Location toEntity(LocationRequest request) {
        Location location = new Location();
        location.setCity(request.city());
        location.setRoomName(request.roomName());
        location.setBuilding(request.building());
        location.setFloor(request.floor());
        return location;
    }

    public static void updateEntity(Location location, LocationRequest request) {
        location.setCity(request.city());
        location.setRoomName(request.roomName());
        location.setBuilding(request.building());
        location.setFloor(request.floor());
    }
}