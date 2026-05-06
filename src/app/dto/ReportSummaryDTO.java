package app.dto;

import java.time.LocalDateTime;

public class ReportSummaryDTO {
    private Long id;
    private String type; 
    private String itemName; 
    private String reporterFullName; 
    private String locationName; 
    private LocalDateTime seenAt; 
    private LocalDateTime foundAt;
    private String claimedStatus;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getReporterFullName() {
		return reporterFullName;
	}
	public void setReporterFullName(String reporterFullName) {
		this.reporterFullName = reporterFullName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public LocalDateTime getSeenAt() {
		return seenAt;
	}
	public void setSeenAt(LocalDateTime seenAt) {
		this.seenAt = seenAt;
	}
	public LocalDateTime getFoundAt() {
		return foundAt;
	}
	public void setFoundAt(LocalDateTime foundAt) {
		this.foundAt = foundAt;
	}
	public String getClaimedStatus() {
		return claimedStatus;
	}
	public void setClaimedStatus(String claimedStatus) {
		this.claimedStatus = claimedStatus;
	}

}
