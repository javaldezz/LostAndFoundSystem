package app.dto;

public class MatchDTO {

	private Long id;
    private Long lostReportId;
    private String lostItemName;
    private Long foundReportId;
    private String foundItemName;
    private String status;
    private double similarityScore;
    private String commonTags; 
    
	public MatchDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLostReportId() {
		return lostReportId;
	}

	public void setLostReportId(Long lostReportId) {
		this.lostReportId = lostReportId;
	}

	public String getLostItemName() {
		return lostItemName;
	}

	public void setLostItemName(String lostItemName) {
		this.lostItemName = lostItemName;
	}

	public Long getFoundReportId() {
		return foundReportId;
	}

	public void setFoundReportId(Long foundReportId) {
		this.foundReportId = foundReportId;
	}

	public String getFoundItemName() {
		return foundItemName;
	}

	public void setFoundItemName(String foundItemName) {
		this.foundItemName = foundItemName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getSimilarityScore() {
		return similarityScore;
	}

	public void setSimilarityScore(double similarityScore) {
		this.similarityScore = similarityScore;
	}

	public String getCommonTags() {
		return commonTags;
	}

	public void setCommonTags(String commonTags) {
		this.commonTags = commonTags;
	}

}

