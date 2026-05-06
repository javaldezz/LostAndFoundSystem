package app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull(message = "Type cannot be null")
	private String type; // LOST, FOUND

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id", nullable = false)
	@NotNull(message = "Item cannot be null")
	private Item item;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reporter_id", nullable = false)
	@NotNull(message = "Reporter cannot be null")
	private Student student;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private Location location;

	@Column
	private LocalDateTime seenAt; // for LOST reports

	@Column
	private LocalDateTime foundAt; // for FOUND reports

	@Column
	@Size(max = 1000, message = "Notes cannot exceed 1000 characters")
	private String notes;

	@Column
	private LocalDateTime createdAt;

	@Column
	@NotNull(message = "Claimed status cannot be null")
	private String claimedStatus; // PENDING, CLAIMED, UNCLAIMED

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "claimed_by_user_id")
	private Student claimedByUser;

	@Column
	private LocalDateTime claimedOn;

	public Report() {
		this.claimedStatus = "PENDING";
		this.createdAt = LocalDateTime.now();
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
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

	public Student getClaimedByUser() {
		return claimedByUser;
	}

	public void setClaimedByUser(Student claimedByUser) {
		this.claimedByUser = claimedByUser;
	}

	public LocalDateTime getClaimedOn() {
		return claimedOn;
	}

	public void setClaimedOn(LocalDateTime claimedOn) {
		this.claimedOn = claimedOn;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", type=" + type + ", itemId=" + (item != null ? item.getId() : null)
				+ ", reporterId=" + (student != null ? student.getId() : null) + ", locationId="
				+ (location != null ? location.getId() : null) + ", seenAt=" + seenAt + ", foundAt=" + foundAt
				+ ", notes=" + notes + ", createdAt=" + createdAt + ", claimedStatus=" + claimedStatus
				+ ", claimedByUserId=" + (claimedByUser != null ? claimedByUser.getId() : null) + ", claimedOn="
				+ claimedOn + "]";
	}

}

