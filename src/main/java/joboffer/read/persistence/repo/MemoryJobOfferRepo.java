package joboffer.read.persistence.repo;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import joboffer.misc.event.JobTitleDuplicated;
import joboffer.read.persistence.dto.JobApplicationDto;
import joboffer.read.persistence.dto.JobOfferDto;

@Repository
public class MemoryJobOfferRepo implements JobOfferRepo {
	private final ApplicationEventPublisher publisher;

	private final ConcurrentHashMap<Object, JobOfferDto> jobOffers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, JobOfferDto> jobOffersByTitle = new ConcurrentHashMap<>();

	@Autowired
	public MemoryJobOfferRepo(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public void save(JobOfferDto jobOffer) {
		// again enforce invariant that job title is unique (because of concurrency
		// issues)
		jobOffers.compute(jobOffer.getId(), (k, v) -> {
			if (v != null && jobOffersByTitle.get(v.getTitle()) != null) {
				// raise an even to handle duplicate job title
				publisher.publishEvent(new JobTitleDuplicated(jobOffer.getTitle(), jobOffer.getId()));
				return v;
			}
			jobOffersByTitle.put(jobOffer.getTitle(), jobOffer);
			return jobOffer;
		});
	}

	@Override
	public Optional<JobOfferDto> findJobOffer(Object jobId) {
		return Optional.ofNullable(jobOffers.get(jobId));
	}

	@Override
	public Stream<JobOfferDto> findAllJobOffers() {
		return jobOffers.values().stream();
	}

	@Override
	public Optional<JobApplicationDto> findJobApplication(Object jobId, String email) {
		Optional<JobOfferDto> job = findJobOffer(jobId);
		if (!job.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(job.get().get(email));
	}

	@Override
	public Optional<JobOfferDto> findJobOfferByTitle(String title) {
		return Optional.ofNullable(jobOffersByTitle.get(title));
	}

}
