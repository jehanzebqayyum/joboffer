package joboffer.domain.event;

import java.util.Date;

import joboffer.domain.JobOffer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractDomainEvent implements DomainEvent<JobOffer> {
	private static final long serialVersionUID = 1L;

	protected final Date timestamp = new Date();
	protected final Object id;
	protected final int version;

	public AbstractDomainEvent(Object id, int version) {
		this.id = id;
		this.version = version;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public Object getAggregateId() {
		return id;
	}

	@Override
	public int getVersion() {
		return version;
	}
}
