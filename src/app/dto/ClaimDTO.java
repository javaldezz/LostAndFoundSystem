package app.dto;

public class ClaimDTO {

	private String claimantEmail;
	private Long claimantId;

	public ClaimDTO() {
	}

	public String getClaimantEmail() {
		return claimantEmail;
	}

	public void setClaimantEmail(String claimantEmail) {
		this.claimantEmail = claimantEmail;
	}

	public Long getClaimantId() {
		return claimantId;
	}

	public void setClaimantId(Long claimantId) {
		this.claimantId = claimantId;
	}
}

