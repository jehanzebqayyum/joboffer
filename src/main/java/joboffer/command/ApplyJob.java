package joboffer.command;

import joboffer.domain.JobOffer;
import lombok.Data;

@Data
public class ApplyJob implements Command<JobOffer> {
	private final Object id;
	private final String email;
	private final String resume;

	@Override
	public Object getAggregateId() {
		return id;
	}
}
