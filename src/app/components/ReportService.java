package app.components;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import app.dto.FoundReportDTO;
import app.dto.ItemDTO;
import app.dto.LostReportDTO;
import app.dto.ReportDTO;
import app.dto.ReportSummaryDTO;
import app.entities.Item;
import app.entities.Location;
import app.entities.Report;
import app.entities.Student;
import app.repositories.ItemRepository;
import app.repositories.LocationRepository;
import app.repositories.ReportRepository;
import app.repositories.StudentRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private MatchingService matchingService;

	public List<ReportSummaryDTO> getAllReports() {
	    return reportRepository.findAll().stream()
	        .map(report -> {
	             ReportSummaryDTO dto = new ReportSummaryDTO();
	             
	             dto.setId(report.getId());
	             dto.setType(report.getType()); 
	             
	             dto.setItemName(report.getItem().getName()); 
	             dto.setReporterFullName(report.getStudent().getFullName());
	             
	             if (report.getLocation() != null) {
	                 String locationName = report.getLocation().getBuilding() + "-" + report.getLocation().getRoom();
	                 dto.setLocationName(locationName); 
	             } else {
	                 dto.setLocationName("N/A");
	             }
	             
	             dto.setSeenAt(report.getSeenAt()); 
	             dto.setFoundAt(report.getFoundAt()); 
	             dto.setClaimedStatus(report.getClaimedStatus());
	             
	             return dto;
	        })
	        .collect(Collectors.toList());
	}
	
	public ReportDTO getReport(Long id) {
		Report report = reportRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 report_not_found"));

		return convertToDTO(report);
	}

	public ReportDTO createLostReport(LostReportDTO dto) {
		// Validate reporter exists
		Student reporter = studentRepository.findById(dto.getReporterId())
				.orElseThrow(() -> new ServiceException("404 student_not_found"));

		// Resolve location
		Location location = null;
		if (dto.getLocationId() != null) {
			location = locationRepository.findById(dto.getLocationId())
					.orElseThrow(() -> new ServiceException("404 location_not_found"));
		} else if (dto.getLocation() != null) {
			location = locationService.findOrCreate(dto.getLocation().getBuilding(), dto.getLocation().getRoom());
			if (dto.getLocation().getLat() != null) {
				location.setLat(dto.getLocation().getLat());
			}
			if (dto.getLocation().getLng() != null) {
				location.setLng(dto.getLocation().getLng());
			}
			location = locationRepository.save(location);
		} else {
			throw new ServiceException("400 validation_error: Location is required");
		}

		// Create or find item
		Item item;
		if (dto.getItem() != null) {
			app.dto.ItemDTO itemDTO = new app.dto.ItemDTO();
			itemDTO.setName(dto.getItem().getName());
			itemDTO.setCategory(dto.getItem().getCategory());
			itemDTO.setDescription(dto.getItem().getDescription());
			itemDTO.setTags(dto.getItem().getTags());
			itemDTO = itemService.createItem(itemDTO);
			item = itemRepository.findById(itemDTO.getId())
					.orElseThrow(() -> new ServiceException("500 internal_error: Failed to retrieve created item"));
		} else {
			throw new ServiceException("400 validation_error: Item is required");
		}

		// Create report
		Report report = new Report();
		report.setType("LOST");
		report.setItem(item);
		report.setStudent(reporter);
		report.setLocation(location);
		report.setSeenAt(dto.getLastSeenAt());
		report.setNotes(dto.getNotes());
		report.setClaimedStatus("UNCLAIMED");
		report.setCreatedAt(LocalDateTime.now());

		report = reportRepository.save(report);

		// Queue for matching
		matchingService.queueForMatching(report.getId());

		return convertToDTO(report);
	}

	public ReportDTO createFoundReport(FoundReportDTO dto) {
		ItemDTO createdItemDTO = itemService.createItem(dto.getItemData());
		
		// Validate item exists
		Item item = itemRepository.findById(createdItemDTO.getId())
                .orElseThrow(() -> new ServiceException("404 item_not_found"));
		
		// Validate reporter exists
		Student reporter = studentRepository.findById(dto.getReporterId())
				.orElseThrow(() -> new ServiceException("404 user_not_found"));

		// Resolve location
		Location location = null;
		if (dto.getLocationId() != null) {
			location = locationRepository.findById(dto.getLocationId())
					.orElseThrow(() -> new ServiceException("404 location_not_found"));
		} else if (dto.getLocation() != null) {
			location = locationService.findOrCreate(dto.getLocation().getBuilding(), dto.getLocation().getRoom());
			if (dto.getLocation().getLat() != null) {
				location.setLat(dto.getLocation().getLat());
			}
			if (dto.getLocation().getLng() != null) {
				location.setLng(dto.getLocation().getLng());
			}
			location = locationRepository.save(location);
		} else {
			throw new ServiceException("400 validation_error: Location is required");
		}

		// Create report
		Report report = new Report();
		report.setType("FOUND");
		report.setItem(item);
		report.setStudent(reporter);
		report.setLocation(location);
		report.setFoundAt(dto.getFoundAt());
		report.setNotes(dto.getNotes());
		report.setClaimedStatus("UNCLAIMED");
		report.setCreatedAt(LocalDateTime.now());

		report = reportRepository.save(report);

		// Queue for matching
		matchingService.queueForMatching(report.getId());

		return convertToDTO(report);
	}

	
	public ReportDTO claimReport(Long reportId, app.dto.ClaimDTO claimDTO) {
		Report report = reportRepository.findById(reportId)
				.orElseThrow(() -> new ServiceException("404 report_not_found"));

		// Resolve claimant
		Student claimant = null;
		if (claimDTO.getClaimantId() != null) {
			claimant = studentRepository.findById(claimDTO.getClaimantId())
					.orElseThrow(() -> new ServiceException("404 user_not_found"));
		} else if (claimDTO.getClaimantEmail() != null) {
			claimant = studentRepository.findByEmail(claimDTO.getClaimantEmail());
			if (claimant == null) {
				throw new ServiceException("404 user_not_found");
			}
		} else {
			throw new ServiceException("400 validation_error: Claimant identifier required");
		}

		// Check if already claimed
		if (!"PENDING".equals(report.getClaimedStatus()) && !"UNCLAIMED".equals(report.getClaimedStatus())) {
			throw new ServiceException("409 has_open_claim");
		}

		// Update report
		report.setClaimedStatus("CLAIMED");
		report.setClaimedByUser(claimant);
		report.setClaimedOn(LocalDateTime.now());
		report = reportRepository.save(report);

		return convertToDTO(report);
	}

	public void deleteReport(Long id) {
		Report report = reportRepository.findById(id)
				.orElseThrow(() -> new ServiceException("404 report_not_found"));

		if (!"PENDING".equals(report.getClaimedStatus()) && !"UNCLAIMED".equals(report.getClaimedStatus())) {
			throw new ServiceException("409 has_open_claim");
		}

		reportRepository.delete(report);
	}

	private ReportDTO convertToDTO(Report report) {
		ReportDTO dto = new ReportDTO();
		dto.setId(report.getId());
		dto.setType(report.getType());
		if (report.getItem() != null) {
		    dto.setItemData(itemService.mapToItemDTO(report.getItem())); 
		}
		dto.setReporterId(report.getStudent() != null ? report.getStudent().getId() : null);
		dto.setLocationId(report.getLocation() != null ? report.getLocation().getId() : null);
		if (report.getLocation() != null) {
			app.dto.LocationDTO locDTO = new app.dto.LocationDTO();
			locDTO.setId(report.getLocation().getId());
			locDTO.setBuilding(report.getLocation().getBuilding());
			locDTO.setRoom(report.getLocation().getRoom());
			locDTO.setLat(report.getLocation().getLat());
			locDTO.setLng(report.getLocation().getLng());
			dto.setLocation(locDTO);
		}
		dto.setSeenAt(report.getSeenAt());
		dto.setFoundAt(report.getFoundAt());
		dto.setNotes(report.getNotes());
		dto.setCreatedAt(report.getCreatedAt());
		dto.setClaimedStatus(report.getClaimedStatus());
		dto.setClaimedByUserId(report.getClaimedByUser() != null ? report.getClaimedByUser().getId() : null);
		dto.setClaimedOn(report.getClaimedOn());
		return dto;
	}
}

