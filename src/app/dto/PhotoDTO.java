package app.dto;

import java.time.LocalDateTime;

public class PhotoDTO {

	private Long photoId;
	private String url;
	private String checksum;
	private LinkedToDTO linkedTo;
	private LocalDateTime uploadedAt;

	public PhotoDTO() {
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

	public LinkedToDTO getLinkedTo() {
		return linkedTo;
	}

	public void setLinkedTo(LinkedToDTO linkedTo) {
		this.linkedTo = linkedTo;
	}

	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public static class LinkedToDTO {
		private Long reportId;
		private Long itemId;

		public Long getReportId() {
			return reportId;
		}

		public void setReportId(Long reportId) {
			this.reportId = reportId;
		}

		public Long getItemId() {
			return itemId;
		}

		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}
	}
}

