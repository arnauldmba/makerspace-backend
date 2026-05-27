package de.whs.makerspace.reservation.dto;

import de.whs.makerspace.reservation.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponse(

        Long id,

        Long userId,
        String userName,

        Long machineId,
        String machineName,

        String roomName,
        String building,
        String floor,

        LocalDateTime startDate,
        LocalDateTime endDate,

        ReservationStatus status,

        String notes
) {
}