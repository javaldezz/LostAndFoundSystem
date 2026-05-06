package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull(message = "Building cannot be null")
	@Size(min = 1, max = 80, message = "Building must be between 1 and 80 characters")
	private String building;

	@Column
	@Size(max = 80, message = "Room cannot exceed 80 characters")
	private String room;

	@Column
	@DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
	@DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
	private Double lat;

	@Column
	@DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
	@DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
	private Double lng;

	public Location() {
	}

	public Location(String building, String room, Double lat, Double lng) {
		this.building = building;
		this.room = room;
		this.lat = lat;
		this.lng = lng;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", building=" + building + ", room=" + room + ", lat=" + lat + ", lng=" + lng
				+ "]";
	}
}

