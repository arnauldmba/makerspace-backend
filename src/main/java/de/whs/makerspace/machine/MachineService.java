package de.whs.makerspace.machine;

import de.whs.makerspace.common.exception.ResourceNotFoundException;
import de.whs.makerspace.location.Location;
import de.whs.makerspace.location.LocationService;
import de.whs.makerspace.machine.dto.MachineRequest;
import de.whs.makerspace.machine.dto.MachineResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final LocationService locationService;

    public MachineService(
            MachineRepository machineRepository,
            LocationService locationService
    ) {
        this.machineRepository = machineRepository;
        this.locationService = locationService;
    }

    public List<MachineResponse> getAllMachines() {
        return machineRepository.findAll()
                .stream()
                .map(MachineMapper::toResponse)
                .toList();
    }

    public MachineResponse getMachineById(Long id) {
        Machine machine = findMachineById(id);
        return MachineMapper.toResponse(machine);
    }

    public MachineResponse createMachine(MachineRequest request) {
        Location location = locationService.findLocationById(request.locationId());

        Machine machine = MachineMapper.toEntity(request, location);
        Machine savedMachine = machineRepository.save(machine);

        return MachineMapper.toResponse(savedMachine);
    }

    public MachineResponse updateMachine(Long id, MachineRequest request) {
        Machine machine = findMachineById(id);
        Location location = locationService.findLocationById(request.locationId());

        MachineMapper.updateEntity(machine, request, location);
        Machine updatedMachine = machineRepository.save(machine);

        return MachineMapper.toResponse(updatedMachine);
    }

    public void deleteMachine(Long id) {
        Machine machine = findMachineById(id);
        machineRepository.delete(machine);
    }

    public Machine findMachineById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id: " + id));
    }
}