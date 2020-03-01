package joboffer.domain.event;

import java.util.Objects;

import joboffer.domain.JobApplicationStatus;
import joboffer.domain.JobOffer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JobApplicationProgressed extends AbstractDomainEvent {
	private static final long serialVersionUID = 8135054492909863153L;

	private final String email;
	private final JobApplicationStatus currentStatus;

	public JobApplicationProgressed(Object id, int version, String email, JobApplicationStatus currentStatus) {
		super(id, version);
		this.email = email;
		this.currentStatus = currentStatus;
	}

	@Override
	public void apply(JobOffer root) {
		root.apply(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobApplicationProgressed other = (JobApplicationProgressed) obj;
		return Objects.equals(email, other.email) && Objects.equals(currentStatus, other.currentStatus)
				&& Objects.equals(version, other.version) && Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, version, timestamp, email, currentStatus);
	}
}
