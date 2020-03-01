package joboffer.domain.event;

import java.util.Date;
import java.util.Objects;

import joboffer.domain.JobOffer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JobOfferCreated extends AbstractDomainEvent {
	private static final long serialVersionUID = -690445288062376827L;

	private final String title;
	private final Date startDate;

	public JobOfferCreated(Object id, int version, String title, Date start) {
		super(id, version);
		this.title = title;
		this.startDate = start;
	}

	@Override
	public void apply(JobOffer root) {
		root.apply(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobOfferCreated other = (JobOfferCreated) obj;
		return Objects.equals(startDate, other.startDate) && Objects.equals(title, other.title)
				&& Objects.equals(version, other.version) && Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, version, timestamp, startDate, title);
	}

}
