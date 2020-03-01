package joboffer.domain;

/**
 * APPLIED -> INVITED, APPLIED -> REJECTED, INVITED -> HIRED, INVITED ->
 * REJECTED
 */
public enum JobApplicationStatus {
	REJECTED(), HIRED(), INVITED(HIRED, REJECTED), APPLIED(INVITED, REJECTED);
	private final JobApplicationStatus[] nextStatuses;

	private JobApplicationStatus(JobApplicationStatus... nextStatuses) {
		this.nextStatuses = nextStatuses;
	}

	public JobApplicationStatus[] getNextStatuses() {
		return nextStatuses;
	}

}
