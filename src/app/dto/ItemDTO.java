package app.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemDTO {

	private Long id;

	@NotNull(message = "Name cannot be null")
	@Size(min = 2, max = 120, message = "Name must be between 2 and 120 characters")
	private String name;

	@NotNull(message = "Category cannot be null")
	private String category;

	@Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;

	private String tags;

	private String status;

	public ItemDTO() {
	}

	public ItemDTO(Long id, String name, String category, String description, String tags, String status) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.tags = tags;
		this.status = status;
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
}

