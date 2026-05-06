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

@Entity
public class NotificationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull(message = "User cannot be null")
	private Student user;

	@Column
	@NotNull(message = "Provider cannot be null")
	private String provider; // TWILIO

	@Column
	private String providerMessageId;

	@Column
	@NotNull(message = "Status cannot be null")
	private String status; // SENT, FAILED

	@Column
	private String messageBody;

	@Column
	private LocalDateTime sentAt;

	public NotificationLog() {
		this.sentAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getUser() {
		return user;
	}

	public void setUser(Student user) {
		this.user = user;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderMessageId() {
		return providerMessageId;
	}

	public void setProviderMessageId(String providerMessageId) {
		this.providerMessageId = providerMessageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	@Override
	public String toString() {
		return "NotificationLog [id=" + id + ", userId=" + (user != null ? user.getId() : null) + ", provider="
				+ provider + ", providerMessageId=" + providerMessageId + ", status=" + status + ", messageBody="
				+ messageBody + ", sentAt=" + sentAt + "]";
	}
}

