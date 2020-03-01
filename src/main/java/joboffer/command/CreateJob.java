package joboffer.command;

import java.util.Date;

import joboffer.domain.JobOffer;
import lombok.Data;

/**
 * create job command
 *
 */
@Data
public class CreateJob implements Command<JobOffer> {
	private final Object id;
	private final String title;
	private final Date startDate;

	@Override
	public Object getAggregateId() {
		return id;
	}
}
