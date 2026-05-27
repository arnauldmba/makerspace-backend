package de.whs.makerspace.machine;

import de.whs.makerspace.machine.dto.MachineRequest;
import de.whs.makerspace.machine.dto.MachineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public List<MachineResponse> getAllMachines() {
        return machineService.getAllMachines();
    }

    @GetMapping("/{id}")
    public MachineResponse getMachineById(@PathVariable("id") Long id) {
        return machineService.getMachineById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MachineResponse createMachine(@Valid @RequestBody MachineRequest request) {
        return machineService.createMachine(request);
    }

    @PutMapping("/{id}")
    public MachineResponse updateMachine(
            @PathVariable("id") Long id,
            @Valid @RequestBody MachineRequest request
    ) {
        return machineService.updateMachine(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMachine(@PathVariable("id") Long id) {
        machineService.deleteMachine(id);
    }
}