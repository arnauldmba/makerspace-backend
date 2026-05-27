package de.whs.makerspace.reservation;

import de.whs.makerspace.machine.Machine;
import de.whs.makerspace.reservation.dto.ReservationRequest;
import de.whs.makerspace.reservation.dto.ReservationResponse;
import de.whs.makerspace.user.User;

public class ReservationMapper {

    public static ReservationResponse toResponse(Reservation reservation) {

        return new ReservationResponse(
            reservation.getId(),

            reservation.getUser().getId(),
            reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName(),

            reservation.getMachine().getId(),
            reservation.getMachine().getName(),

            reservation.getMachine().getLocation().getRoomName(),
            reservation.getMachine().getLocation().getBuilding(),
            reservation.getMachine().getLocation().getFloor(),

            reservation.getStartDate(),
            reservation.getEndDate(),

            reservation.getStatus(),

            reservation.getNotes()
        );
    }

    public static Reservation toEntity(
            ReservationRequest request,
            User user,
            Machine machine
    ) {

        return Reservation.builder()
                .user(user)
                .machine(machine)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .status(ReservationStatus.PENDING)
                .notes(request.notes())
                .build();
    }
}