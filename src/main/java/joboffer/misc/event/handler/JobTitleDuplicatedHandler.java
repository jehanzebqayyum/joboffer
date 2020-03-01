package joboffer.misc.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import joboffer.eventstore.EventStore;
import joboffer.misc.event.JobTitleDuplicated;

/**
 * a compensating event handler which ensures eventual consistency by enforcing
 * job title uniqueness at later stage
 *
 */
@Component
public class JobTitleDuplicatedHandler {
	private final EventStore store;

	@Autowired
	public JobTitleDuplicatedHandler(EventStore store) {
		this.store = store;
	}

	@Async
	@EventListener
	public void handle(JobTitleDuplicated event) {
		// delete duplicate data from event store
		store.delete(event.getDuplicatedJobId());
		// TODO: do compensating actions e.g. notify/email user
	}

}
