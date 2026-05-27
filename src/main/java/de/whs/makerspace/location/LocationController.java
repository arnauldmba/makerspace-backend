package de.whs.makerspace.location;

import de.whs.makerspace.location.dto.LocationRequest;
import de.whs.makerspace.location.dto.LocationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationResponse> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public LocationResponse getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponse createLocation(@Valid @RequestBody LocationRequest request) {
        return locationService.createLocation(request);
    }

    @PutMapping("/{id}")
    public LocationResponse updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody LocationRequest request
    ) {
        return locationService.updateLocation(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }
}
