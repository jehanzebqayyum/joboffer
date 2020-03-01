package joboffer.read.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import joboffer.domain.event.JobApplied;
import joboffer.read.persistence.dto.JobApplicationDto;
import joboffer.read.persistence.repo.JobOfferRepo;

@Component
public class JobAppliedHandler {
	private final JobOfferRepo repo;

	@Autowired
	public JobAppliedHandler(JobOfferRepo repo) {
		this.repo = repo;
	}

	// should use @TransactionalEventListener for relational eventstore
	@Async
	@EventListener
	public void handle(JobApplied event) {
		this.repo.findJobOffer(event.getAggregateId()).ifPresent(job -> {
			JobApplicationDto dto = new JobApplicationDto();
			dto.setEmail(event.getEmail());
			dto.setResume(event.getResume());
			job.put(dto);
		});
	}
}
