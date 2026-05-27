package de.whs.makerspace.location.dto;

public record LocationResponse(
        Long id,
        String city,
        String roomName,
        String building,
        String floor
) {
}
