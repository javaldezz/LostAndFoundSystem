package app.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class StudentDTO {

	private Long id;

	@NotNull(message = "Full name cannot be null")
	@Size(min = 2, max = 200, message = "Full name must be between 2 and 200 characters")
	private String fullName;

	@NotNull(message = "Email cannot be null")
	@Email(message = "Email must be valid")
	private String email;

	@NotNull(message = "Phone cannot be null")
	@Pattern(regexp = "^\\+63\\d{10}$", message = "Phone must be in format +63XXXXXXXXXX")
	private String phone;

	@NotNull(message = "Role cannot be null")
	private String role; // STUDENT, ADMIN

	private LocalDateTime createdAt;

	public StudentDTO() {
	}

	public StudentDTO(Long id, String fullName, String email, String phone, String role) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}

