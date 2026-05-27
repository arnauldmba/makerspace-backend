package de.whs.makerspace.reservation;

import de.whs.makerspace.common.exception.ResourceNotFoundException;
import de.whs.makerspace.machine.Machine;
import de.whs.makerspace.machine.MachineService;
import de.whs.makerspace.reservation.dto.ReservationRequest;
import de.whs.makerspace.reservation.dto.ReservationResponse;
import de.whs.makerspace.user.User;
import de.whs.makerspace.user.UserRepository;
import de.whs.makerspace.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final MachineService machineService;
    private final UserRepository userRepository;

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    public ReservationResponse getReservationById(Long id) {

        Reservation reservation = findReservationById(id);

        return ReservationMapper.toResponse(reservation);
    }

    private User getAuthenticatedUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    public ReservationResponse createReservation(ReservationRequest request) {

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        User user = getAuthenticatedUser();

        Machine machine = machineService.findMachineById(request.machineId());

        boolean hasConflict = !reservationRepository
                .findByMachineAndStartDateLessThanAndEndDateGreaterThan(
                        machine,
                        request.endDate(),
                        request.startDate()
                )
                .isEmpty();

        if (hasConflict) {
            throw new IllegalArgumentException("Machine is already reserved during this period");
        }

        Reservation reservation = ReservationMapper.toEntity(
                request,
                user,
                machine
        );

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationMapper.toResponse(savedReservation);
    }

    public List<ReservationResponse> getMyReservations() {

        User user = getAuthenticatedUser();

        return reservationRepository.findByUserId(user.getId())
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    public void deleteReservation(Long id) {

        Reservation reservation = findReservationById(id);

        reservationRepository.delete(reservation);
    }

    public Reservation findReservationById(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reservation not found with id: " + id
                        )
                );
    }

    public ReservationResponse approveReservation(Long id) {
        Reservation reservation = findReservationById(id);

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("Only pending reservations can be approved");
        }

        reservation.setStatus(ReservationStatus.APPROVED);

        return ReservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse rejectReservation(Long id) {
        Reservation reservation = findReservationById(id);

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("Only pending reservations can be rejected");
        }

        reservation.setStatus(ReservationStatus.REJECTED);

        return ReservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = findReservationById(id);

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("Reservation is already cancelled");
        }

        if (reservation.getStatus() == ReservationStatus.REJECTED) {
            throw new IllegalArgumentException("Rejected reservations cannot be cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        return ReservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> getReservationsByUser(Long userId) {
        userService.findUserById(userId);

        return reservationRepository.findByUserId(userId)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    public List<ReservationResponse> getReservationsByMachine(Long machineId) {
        machineService.findMachineById(machineId);

        return reservationRepository.findByMachineId(machineId)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    public List<ReservationResponse> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    public List<String> getReservedSlots2(
        Long machineId,
        LocalDate date
    ) {

        LocalDateTime startOfDay = date.atStartOfDay();

        LocalDateTime endOfDay =
                date.atTime(LocalTime.MAX);

        List<Reservation> reservations =
            reservationRepository
                    .findByMachineIdAndStartDateBetween(
                            machineId,
                            startOfDay,
                            endOfDay
                    );

        return reservations.stream()
            .map(reservation -> {

                String start =
                        reservation.getStartDate()
                                .toLocalTime()
                                .toString();

                String end =
                        reservation.getEndDate()
                                .toLocalTime()
                                .toString();

                return start.substring(0, 5)
                        + " - "
                        + end.substring(0, 5);
            })
            .toList();
    }
    

    public List<String> getReservedSlots(
        Long machineId,
        LocalDate date
    ) {

        LocalDateTime startOfDay = date.atStartOfDay();

        LocalDateTime endOfDay =
                date.atTime(LocalTime.MAX);

        List<Reservation> reservations =
                reservationRepository
                        .findByMachineIdAndStartDateBetween(
                                machineId,
                                startOfDay,
                                endOfDay
                        );

        return reservations.stream()
            .flatMap(reservation -> {

                LocalTime start =
                        reservation.getStartDate()
                                .toLocalTime();

                LocalTime end =
                        reservation.getEndDate()
                                .toLocalTime();

                List<String> slots = new ArrayList<>();

                LocalTime current = start;

                while (current.isBefore(end)) {

                    LocalTime next = current.plusHours(1);

                    slots.add(
                            current.toString().substring(0, 5)
                                    + " - "
                                    + next.toString().substring(0, 5)
                    );

                    current = next;
                }

                return slots.stream();
            })
            .distinct()
            .toList();
    }
}
