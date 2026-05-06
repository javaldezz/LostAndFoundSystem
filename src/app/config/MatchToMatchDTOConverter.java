package app.config; 

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import app.dto.MatchDTO;
import app.entities.Match;
import app.entities.Report;
import app.entities.Item;

@Component
public class MatchToMatchDTOConverter implements Converter<Match, MatchDTO> {

    @Override
    public MatchDTO convert(Match match) {
        if (match == null) {
            return null;
        }
        
        MatchDTO dto = new MatchDTO();
        
        // Set direct fields
        dto.setId(match.getId());
        dto.setStatus(match.getStatus());
        dto.setSimilarityScore(match.getSimilarityScore());

        // Safely extract LOST Report/Item data
        Report lostReport = match.getLostReport();
        if (lostReport != null) {
            dto.setLostReportId(lostReport.getId());
            
            Item lostItem = lostReport.getItem();
            if (lostItem != null) {
                dto.setLostItemName(lostItem.getName()); 
            } else {
                dto.setLostItemName("[Missing Lost Item]");
            }
        } else {
            dto.setLostItemName("[Missing Lost Report]");
        }

        // Safely extract FOUND Report/Item data
        Report foundReport = match.getFoundReport();
        if (foundReport != null) {
            dto.setFoundReportId(foundReport.getId());
            
            Item foundItem = foundReport.getItem();
            if (foundItem != null) {
                dto.setFoundItemName(foundItem.getName());
            } else {
                dto.setFoundItemName("[Missing Found Item]");
            }
        } else {
             dto.setFoundItemName("[Missing Found Report]");
        }
        
        dto.setCommonTags(match.getCommonTags()); 

        return dto;
    }
}