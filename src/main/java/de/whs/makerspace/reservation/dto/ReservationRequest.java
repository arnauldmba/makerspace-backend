package de.whs.makerspace.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationRequest(

        @NotNull(message = "Machine id is required")
        Long machineId,

        @NotNull(message = "Start date is required")
        @Future(message = "Start date must be in the future")
        LocalDateTime startDate,

        @NotNull(message = "End date is required")
        @Future(message = "End date must be in the future")
        LocalDateTime endDate,

        String notes
) {
}
