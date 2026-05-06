package app.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import app.dto.LocationDTO;
import app.entities.Location;
import app.repositories.LocationRepository;
import app.repositories.ReportRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ReportRepository reportRepository;
	
	public List<LocationDTO> getAllLocations() {
	    return locationRepository.findAll().stream()
	        // Replace mapToDTO with your actual conversion logic
	        .map(location -> {
	             LocationDTO dto = new LocationDTO();
	             dto.setId(location.getId());
	             dto.setBuilding(location.getBuilding());
	             dto.setRoom(location.getRoom());
	             dto.setLat(location.getLat());
	             dto.setLng(location.getLng());
	             return dto;
	        }) 
	        .collect(Collectors.toList());
	}

	public LocationDTO getById(Long id) {
		Location location = locationRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 location_not_found"));

		LocationDTO dto = new LocationDTO();
		dto.setId(location.getId());
		dto.setBuilding(location.getBuilding());
		dto.setRoom(location.getRoom());
		dto.setLat(location.getLat());
		dto.setLng(location.getLng());
		return dto;
	}

	public LocationDTO create(LocationDTO dto) {
		// Validate building
		if (dto.getBuilding() == null || dto.getBuilding().length() < 1 || dto.getBuilding().length() > 80) {
			throw new ServiceException("400 validation_error: Building must be between 1 and 80 characters");
		}

		// Check duplicate
		Location existing = locationRepository.findByBuildingAndRoom(dto.getBuilding(), dto.getRoom());
		if (existing != null) {
			throw new ServiceException("409 duplicate_location");
		}

		Location location = new Location();
		location.setBuilding(dto.getBuilding());
		location.setRoom(dto.getRoom());
		location.setLat(dto.getLat());
		location.setLng(dto.getLng());

		location = locationRepository.save(location);

		LocationDTO result = new LocationDTO();
		result.setId(location.getId());
		result.setBuilding(location.getBuilding());
		result.setRoom(location.getRoom());
		result.setLat(location.getLat());
		result.setLng(location.getLng());
		return result;
	}

	public void delete(Long id) {
		Location location = locationRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 location_not_found"));

		// Check if linked to reports
		long count = reportRepository.findAll().stream()
				.filter(r -> r.getLocation() != null && r.getLocation().getId().equals(id))
				.count();
		if (count > 0) {
			throw new ServiceException("409 has_linked_reports");
		}

		locationRepository.delete(location);
	}

	public Location findOrCreate(String building, String room) {
		Location location = locationRepository.findByBuildingAndRoom(building, room);
		if (location == null) {
			location = new Location();
			location.setBuilding(building);
			location.setRoom(room);
			location = locationRepository.save(location);
		}
		return location;
	}
}

