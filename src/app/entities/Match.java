package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lost_report_id", nullable = false)
	@NotNull(message = "Lost report cannot be null")
	private Report lostReport;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "found_report_id", nullable = false)
	@NotNull(message = "Found report cannot be null")
	private Report foundReport;

	@Column
	@NotNull(message = "Status cannot be null")
	private String status; // NEW, REVIEWED, ACCEPTED, REJECTED

	@Column
	private Double similarityScore;
	
	@Column
    private String commonTags; 


	public String getCommonTags() {
		return commonTags;
	}

	public void setCommonTags(String commonTags) {
		this.commonTags = commonTags;
	}

	public Match() {
		this.status = "NEW";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Report getLostReport() {
		return lostReport;
	}

	public void setLostReport(Report lostReport) {
		this.lostReport = lostReport;
	}

	public Report getFoundReport() {
		return foundReport;
	}

	public void setFoundReport(Report foundReport) {
		this.foundReport = foundReport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSimilarityScore() {
		return similarityScore;
	}

	public void setSimilarityScore(Double similarityScore) {
		this.similarityScore = similarityScore;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", lostReportId=" + (lostReport != null ? lostReport.getId() : null)
				+ ", foundReportId=" + (foundReport != null ? foundReport.getId() : null) + ", status=" + status
				+ ", similarityScore=" + similarityScore + "]";
	}
}

