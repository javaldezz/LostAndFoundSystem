package app.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull(message = "Name cannot be null")
	@Size(min = 2, max = 120, message = "Name must be between 2 and 120 characters")
	private String name;

	@Column
	@NotNull(message = "Category cannot be null")
	private String category; 

	@Column
	@Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;

	@Column(name = "tags")
	private String tags;

	@Column
	private String status; 

	@Column
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	public Item() {
		this.status = "ACTIVE";
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public Item(String name, String category, String description, String tags) {
		this();
		this.name = name;
		this.category = category;
		this.description = description;
		if (tags != null) {
			this.tags = tags;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", tags=" + tags + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
}

