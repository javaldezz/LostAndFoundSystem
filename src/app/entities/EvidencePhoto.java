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

@Entity
public class EvidencePhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photo_id")
	private Long photoId;

	@Column
	private String url;

	@Column
	private String checksum; // SHA256 hash

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_id")
	private Report report;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private Item item;

	@Column
	private LocalDateTime uploadedAt;

	public EvidencePhoto() {
		this.uploadedAt = LocalDateTime.now();
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	@Override
	public String toString() {
		return "EvidencePhoto [photoId=" + photoId + ", url=" + url + ", checksum=" + checksum + ", reportId="
				+ (report != null ? report.getId() : null) + ", itemId=" + (item != null ? item.getId() : null)
				+ ", uploadedAt=" + uploadedAt + "]";
	}
}

