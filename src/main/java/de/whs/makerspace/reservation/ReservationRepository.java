package de.whs.makerspace.reservation;

import de.whs.makerspace.machine.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMachineAndStartDateLessThanAndEndDateGreaterThan(
            Machine machine,
            LocalDateTime endDate,
            LocalDateTime startDate
    );

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByMachineId(Long machineId);

    List<Reservation> findByStatus(ReservationStatus status);

    List<Reservation> findByMachineIdAndStartDateBetween(Long machineId, LocalDateTime startDate, LocalDateTime endDate);
}