package joboffer.command;

import joboffer.domain.JobApplicationStatus;
import joboffer.domain.JobOffer;
import lombok.Data;

/**
 * change the status of job application
 *
 */
@Data
public class ProgressJobApplication implements Command<JobOffer> {
	private final Object id;
	private final String email;
	private final JobApplicationStatus nextStatus;

	@Override
	public Object getAggregateId() {
		return id;
	}

}
