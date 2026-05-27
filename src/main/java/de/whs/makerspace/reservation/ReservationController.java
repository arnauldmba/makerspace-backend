package de.whs.makerspace.reservation;

import de.whs.makerspace.reservation.dto.ReservationRequest;
import de.whs.makerspace.reservation.dto.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ReservationResponse getReservationById(@PathVariable("id") Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/my")
    public List<ReservationResponse> getMyReservations() {
        return reservationService.getMyReservations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(
            @Valid @RequestBody ReservationRequest request
    ) {

        return reservationService.createReservation(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("id") Long id) {

        reservationService.deleteReservation(id);
    }

    @PatchMapping("/{id}/approve")
    public ReservationResponse approveReservation(@PathVariable("id") Long id) {
        return reservationService.approveReservation(id);
    }

    @PatchMapping("/{id}/reject")
    public ReservationResponse rejectReservation(@PathVariable("id") Long id) {
        return reservationService.rejectReservation(id);
    }

    @PatchMapping("/{id}/cancel")
    public ReservationResponse cancelReservation(@PathVariable("id") Long id) {
        return reservationService.cancelReservation(id);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationResponse> getReservationsByUser(@PathVariable("userId") Long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping("/machine/{machineId}")
    public List<ReservationResponse> getReservationsByMachine(@PathVariable("machineId") Long machineId) {
        return reservationService.getReservationsByMachine(machineId);
    }

    @GetMapping("/status/{status}")
    public List<ReservationResponse> getReservationsByStatus(@PathVariable("status") ReservationStatus status) {
        return reservationService.getReservationsByStatus(status);
    }

    @GetMapping("/machine/{machineId}/date/{date}")
    public List<String> getReservedSlots(
            @PathVariable("machineId")  Long machineId,
            @PathVariable("date") LocalDate date
    ) {

        return reservationService
                .getReservedSlots(machineId, date);
    }
}