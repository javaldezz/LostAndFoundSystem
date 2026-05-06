package app.dto;

import java.time.LocalDateTime;

public class ReportDTO {

	private Long id;
	private String type;
	private ItemDTO itemData;
	private Long reporterId;
	private StudentDTO student;
	private Long locationId;
	private LocationDTO location;
	private LocalDateTime seenAt;
	private LocalDateTime foundAt;
	private String notes;
	private LocalDateTime createdAt;
	private String claimedStatus;
	private Long claimedByUserId;
	private LocalDateTime claimedOn;

	public ReportDTO() {
	}

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

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public LocalDateTime getClaimedOn() {
		return claimedOn;
	}

	public void setClaimedOn(LocalDateTime claimedOn) {
		this.claimedOn = claimedOn;
	}

	public ItemDTO getItemData() {
		return itemData;
	}

	public void setItemData(ItemDTO itemData) {
		this.itemData = itemData;
	}
}

