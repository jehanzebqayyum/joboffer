package joboffer.domain;

import java.util.Arrays;

import joboffer.domain.exception.InvalidProgressException;
import lombok.EqualsAndHashCode;

/**
 * Entity. Package private to protect access except from aggregate root.
 * 
 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JobApplication {
	@EqualsAndHashCode.Include
	private String email;
	private String resume;
	private JobApplicationStatus status;

	JobApplication(String email, String resume) {
		super();
		this.email = email;
		this.resume = resume;
		this.status = JobApplicationStatus.APPLIED;
	}

	// package private to protect access but from aggregate root
	void progress(JobApplicationStatus nextStatus) {
		if (!Arrays.asList(status.getNextStatuses()).contains(nextStatus)) {
			throw new InvalidProgressException();
		}
		this.status = nextStatus;
	}

	public String getEmail() {
		return email;
	}

	public String getResume() {
		return resume;
	}

	public JobApplicationStatus getStatus() {
		return status;
	}

}
