package app.dto;

public class MatchDetailDTO {

    private Long id;
    private String status;
    private double similarityScore;
    private String commonTags;
    
    private String lostReporterPhone; 
    private String lostReporterEmail;
    
    private ReportDTO lostReportDetails;
    private ReportDTO foundReportDetails;

    public MatchDetailDTO() {}

    
    public String getLostReporterPhone() {
        return lostReporterPhone;
    }

    public void setLostReporterPhone(String lostReporterPhone) {
        this.lostReporterPhone = lostReporterPhone;
    }

    public String getLostReporterEmail() {
        return lostReporterEmail;
    }

    public void setLostReporterEmail(String lostReporterEmail) {
        this.lostReporterEmail = lostReporterEmail;
    }
    
    // --- Existing Getters and Setters (omitted for brevity) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ReportDTO getLostReportDetails() {
        return lostReportDetails;
    }

    public void setLostReportDetails(ReportDTO lostReportDetails) {
        this.lostReportDetails = lostReportDetails;
    }

    public ReportDTO getFoundReportDetails() {
        return foundReportDetails;
    }

    public void setFoundReportDetails(ReportDTO foundReportDetails) {
        this.foundReportDetails = foundReportDetails;
    }
}