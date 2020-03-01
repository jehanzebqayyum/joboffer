package joboffer.domain.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import joboffer.domain.event.JobApplicationProgressed;

@Component
public class NotificationHandler {

	// should use @TransactionalEventListener for relational eventstore
	@Async
	@EventListener
	public void handle(JobApplicationProgressed event) {
		System.out.format("notification: jobId: %s, email: %s, status: %s", event.getAggregateId(), event.getEmail(),
				event.getCurrentStatus());
	}
}
