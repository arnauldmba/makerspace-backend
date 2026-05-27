package de.whs.makerspace.location;

import de.whs.makerspace.common.exception.ResourceNotFoundException;
import de.whs.makerspace.location.dto.LocationRequest;
import de.whs.makerspace.location.dto.LocationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(LocationMapper::toResponse)
                .toList();
    }

    public LocationResponse getLocationById(Long id) {
        Location location = findLocationById(id);
        return LocationMapper.toResponse(location);
    }

    public LocationResponse createLocation(LocationRequest request) {
        Location location = LocationMapper.toEntity(request);
        Location savedLocation = locationRepository.save(location);
        return LocationMapper.toResponse(savedLocation);
    }

    public LocationResponse updateLocation(Long id, LocationRequest request) {
        Location location = findLocationById(id);
        LocationMapper.updateEntity(location, request);
        Location updatedLocation = locationRepository.save(location);
        return LocationMapper.toResponse(updatedLocation);
    }

    public void deleteLocation(Long id) {
        Location location = findLocationById(id);
        locationRepository.delete(location);
    }

    public Location findLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
    }
}
