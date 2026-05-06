package app.components;

import org.springframework.stereotype.Component;
import app.entities.Match;
import app.entities.Report;
import app.entities.Item;
import app.entities.Student; 
import app.entities.Location; 
import app.dto.MatchDetailDTO;
import app.dto.ReportDTO;
import app.dto.ItemDTO;
import app.dto.LocationDTO;

@Component
public class MatchDetailMapper {

    
    public MatchDetailDTO mapToMatchDetailDTO(Match match) {
        MatchDetailDTO dto = new MatchDetailDTO();
        dto.setId(match.getId());
        dto.setStatus(match.getStatus());
        dto.setSimilarityScore(match.getSimilarityScore() != null ? match.getSimilarityScore() : 0.0);
        dto.setCommonTags(match.getCommonTags() != null ? match.getCommonTags() : "None");
        
        if (match.getLostReport() != null) {
            ReportDTO lostDto = mapReportToDTO(match.getLostReport());
            dto.setLostReportDetails(lostDto);
            
            Student lostStudent = match.getLostReport().getStudent(); 
            if (lostStudent != null) {
                 dto.setLostReporterPhone(lostStudent.getPhone());
                 dto.setLostReporterEmail(lostStudent.getEmail());
            }
        }

        if (match.getFoundReport() != null) {
            dto.setFoundReportDetails(mapReportToDTO(match.getFoundReport()));
        }

        return dto;
    }
    
    
    private ReportDTO mapReportToDTO(Report report) {
        ReportDTO dto = new ReportDTO();
        
        dto.setId(report.getId());
        dto.setType(report.getType());

        dto.setReporterId(report.getStudent() != null ? report.getStudent().getId() : null); 
        dto.setLocationId(report.getLocation() != null ? report.getLocation().getId() : null);
        dto.setSeenAt(report.getSeenAt());
        dto.setFoundAt(report.getFoundAt());
        dto.setNotes(report.getNotes());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setClaimedStatus(report.getClaimedStatus());
        dto.setClaimedOn(report.getClaimedOn());

        
        if (report.getItem() != null) {
            dto.setItemData(mapItemToDTO(report.getItem())); 
        }

        if (report.getLocation() != null) {
            dto.setLocation(mapLocationToDTO(report.getLocation())); 
        }
        
        
        return dto;
    }
    
    
    private ItemDTO mapItemToDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setCategory(item.getCategory());
        dto.setDescription(item.getDescription());
        dto.setTags(item.getTags());
        dto.setStatus(item.getStatus());
        return dto;
    }
    

    private LocationDTO mapLocationToDTO(Location location) {
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setBuilding(location.getBuilding());
        dto.setRoom(location.getRoom());
        dto.setLat(location.getLat());
        dto.setLng(location.getLng());
        return dto;
    }
    
}