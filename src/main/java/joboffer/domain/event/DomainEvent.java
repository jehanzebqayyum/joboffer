package joboffer.domain.event;

import joboffer.eventstore.Event;

public interface DomainEvent<T> extends Event {
	/**
	 * Apply event on the given aggregate root. Runtime method dispatching.
	 */
	void apply(T root);
}
