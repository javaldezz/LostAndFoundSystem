package app.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class LostReportDTO {

	@NotNull(message = "Reporter ID cannot be null")
	private Long reporterId;

	@NotNull(message = "Item cannot be null")
	private ItemDTO item;

	private Long locationId;

	private LocationDTO location;

	@NotNull(message = "Last seen at cannot be null")
	private LocalDateTime lastSeenAt;

	private String notes;

	public LostReportDTO() {
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
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

	public LocalDateTime getLastSeenAt() {
		return lastSeenAt;
	}

	public void setLastSeenAt(LocalDateTime lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}

