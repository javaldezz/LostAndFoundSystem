package app.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 
import app.dto.DashboardSummaryDTO;
import app.repositories.ItemRepository;
import app.repositories.LocationRepository;
import app.repositories.ReportRepository; 
import app.repositories.StudentRepository; 

@Component 
public class DashboardService {

    @Autowired private ItemRepository itemRepository;
    @Autowired private LocationRepository locationRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private StudentRepository studentRepository;

    public DashboardSummaryDTO getSummaryCounts() {
        DashboardSummaryDTO dto = new DashboardSummaryDTO();
        
        dto.setItems(itemRepository.count());
        dto.setLocations(locationRepository.count());
        dto.setStudents(studentRepository.count());
        dto.setLostReports(reportRepository.countByType("LOST"));
        dto.setFoundReports(reportRepository.countByType("FOUND"));        
        return dto;
    }
}