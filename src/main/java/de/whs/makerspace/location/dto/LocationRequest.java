package de.whs.makerspace.location.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationRequest(

        @NotBlank(message = "City name is required")
        String city,

        @NotBlank(message = "Room name is required")
        String roomName,

        @NotBlank(message = "Building is required")
        String building,

        @NotBlank(message = "Floor is required")
        String floor
) {
}
