package app.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class FoundReportDTO {

	@NotNull(message = "Reporter ID cannot be null")
	private Long reporterId;

	@NotNull(message = "Item data cannot be null")
	private ItemDTO itemData; 
	
	private Long locationId;

	private LocationDTO location;

	@NotNull(message = "Found at cannot be null")
	private LocalDateTime foundAt;

	private String notes;

	public FoundReportDTO() {
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}
	
	public ItemDTO getItemData() {
        return itemData;
    }

    public void setItemData(ItemDTO itemData) {
        this.itemData = itemData;
    }

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public LocalDateTime getFoundAt() {
		return foundAt;
	}

	public void setFoundAt(LocalDateTime foundAt) {
		this.foundAt = foundAt;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}

