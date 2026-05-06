package app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull(message = "Full name cannot be null")
	@Size(min = 2, max = 200, message = "Full name must be between 2 and 200 characters")
	private String fullName;

	@Column(unique = true)
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email must be valid")
	private String email;

	@Column
	@NotNull(message = "Phone cannot be null")
	@Pattern(regexp = "^\\+63\\d{10}$", message = "Phone must be in format +63XXXXXXXXXX")
	private String phone;

	@Column
	@NotNull(message = "Role cannot be null")
	private String role; // STUDENT, ADMIN

	@Column
	private LocalDateTime createdAt;

	public Student() {
	}

	public Student(String fullName, String email, String phone, String role) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.createdAt = LocalDateTime.now();
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

	@Override
	public String toString() {
		return "Student [id=" + id + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", role="
				+ role + ", createdAt=" + createdAt + "]";
	}
}

