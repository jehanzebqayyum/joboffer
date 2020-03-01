package joboffer.read.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import joboffer.domain.event.JobOfferCreated;
import joboffer.read.persistence.dto.JobOfferDto;
import joboffer.read.persistence.repo.JobOfferRepo;

@Component
public class JobOfferCreatedHandler {
	private final JobOfferRepo repo;

	@Autowired
	public JobOfferCreatedHandler(JobOfferRepo repo) {
		this.repo = repo;
	}

	// should use @TransactionalEventListener for relational eventstore
	@Async
	@EventListener
	public void handle(JobOfferCreated event) {
		JobOfferDto dto = new JobOfferDto();
		dto.setId(event.getAggregateId());
		dto.setTitle(event.getTitle());
		dto.setStartDate(event.getStartDate());

		this.repo.save(dto);
	}
}
