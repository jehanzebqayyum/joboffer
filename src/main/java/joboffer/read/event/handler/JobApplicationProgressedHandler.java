package joboffer.read.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import joboffer.domain.event.JobApplicationProgressed;
import joboffer.read.persistence.repo.JobOfferRepo;

@Component
public class JobApplicationProgressedHandler {
	private final JobOfferRepo repo;

	@Autowired
	public JobApplicationProgressedHandler(JobOfferRepo repo) {
		this.repo = repo;
	}

	// should use @TransactionalEventListener for relational eventstore
	@Async
	@EventListener
	public void handle(JobApplicationProgressed event) {
		this.repo.findJobApplication(event.getAggregateId(), event.getEmail()).ifPresent(app -> {
			app.setStatus(event.getCurrentStatus().toString());
		});
	}
}
