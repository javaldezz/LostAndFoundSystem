package app.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.ReportService;
import app.dto.ClaimDTO;
import app.dto.FoundReportDTO;
import app.dto.LostReportDTO;
import app.dto.PhotoDTO;
import app.dto.ReportDTO;
import app.dto.ReportSummaryDTO;
import app.dto.StatusDTO;
import app.entities.EvidencePhoto;
import app.repositories.EvidencePhotoRepository;

@Component
@Path("/api/reports")
public class ReportsController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private EvidencePhotoRepository photoRepository;

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReports() {
        List<ReportSummaryDTO> dtos = reportService.getAllReports(); 
        return Response.ok(dtos).build();
    }
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport(@PathParam("id") Long id) {
		ReportDTO dto = reportService.getReport(id);

		// Load photos
		List<EvidencePhoto> photos = photoRepository.findByReportId(id);
		List<PhotoDTO> photoDTOs = photos.stream().map(photo -> {
			PhotoDTO photoDTO = new PhotoDTO();
			photoDTO.setPhotoId(photo.getPhotoId());
			photoDTO.setUrl(photo.getUrl());
			return photoDTO;
		}).collect(Collectors.toList());

		// Create response with photos
		ReportResponseDTO response = new ReportResponseDTO();
		response.setId(dto.getId());
		response.setType(dto.getType());
		response.setItemData(dto.getItemData());
		response.setReporterId(dto.getReporterId());
		response.setLocationId(dto.getLocationId());
		response.setLocation(dto.getLocation());
		response.setSeenAt(dto.getSeenAt());
		response.setFoundAt(dto.getFoundAt());
		response.setNotes(dto.getNotes());
		response.setCreatedAt(dto.getCreatedAt());
		response.setClaimedStatus(dto.getClaimedStatus());
		response.setClaimedByUserId(dto.getClaimedByUserId());
		response.setClaimedOn(dto.getClaimedOn());
		response.setPhotos(photoDTOs);

		return Response.ok(response).build();
	}

	@POST
	@Path("/lost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createLostReport(LostReportDTO dto) {
		ReportDTO result = reportService.createLostReport(dto);
		return Response.ok(result).build();
	}

	@POST
	@Path("/found")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createFoundReport(FoundReportDTO dto) {
		ReportDTO result = reportService.createFoundReport(dto);
		return Response.ok(result).build();
	}

	@POST
	@Path("/{id}/claim")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response claimReport(@PathParam("id") Long id, ClaimDTO claimDTO) {
		ReportDTO result = reportService.claimReport(id, claimDTO);
		ClaimResponseDTO response = new ClaimResponseDTO();
		response.setId(result.getId());
		response.setClaimedStatus(result.getClaimedStatus());
		response.setClaimedByUserId(result.getClaimedByUserId());
		response.setClaimedOn(result.getClaimedOn());
		return Response.ok(response).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReport(@PathParam("id") Long id) {
		reportService.deleteReport(id);
		return Response.ok(new StatusDTO("deleted")).build();
	}

	// Helper classes for response
	public static class ReportResponseDTO extends ReportDTO {
		private List<PhotoDTO> photos = new ArrayList<>();

		public List<PhotoDTO> getPhotos() {
			return photos;
		}

		public void setPhotos(List<PhotoDTO> photos) {
			this.photos = photos;
		}
	}

	public static class ClaimResponseDTO {
		private Long id;
		private String claimedStatus;
		private Long claimedByUserId;
		private java.time.LocalDateTime claimedOn;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getClaimedStatus() {
			return claimedStatus;
		}

		public void setClaimedStatus(String claimedStatus) {
			this.claimedStatus = claimedStatus;
		}

		public Long getClaimedByUserId() {
			return claimedByUserId;
		}

		public void setClaimedByUserId(Long claimedByUserId) {
			this.claimedByUserId = claimedByUserId;
		}

		public java.time.LocalDateTime getClaimedOn() {
			return claimedOn;
		}

		public void setClaimedOn(java.time.LocalDateTime claimedOn) {
			this.claimedOn = claimedOn;
		}
	}
}

