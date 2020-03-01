package joboffer.domain.event;

import java.util.Objects;

import joboffer.domain.JobOffer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JobApplied extends AbstractDomainEvent {
	private static final long serialVersionUID = -8679307932287745560L;

	private final String email;
	private final String resume;

	public JobApplied(Object id, int version, String email, String resume) {
		super(id, version);
		this.email = email;
		this.resume = resume;
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
		JobApplied other = (JobApplied) obj;
		return Objects.equals(email, other.email) && Objects.equals(resume, other.resume)
				&& Objects.equals(version, other.version) && Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, version, timestamp, email, resume);
	}
}
