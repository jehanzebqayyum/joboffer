package joboffer.read.persistence.repo;

import java.util.Optional;
import java.util.stream.Stream;

import joboffer.read.persistence.dto.JobApplicationDto;
import joboffer.read.persistence.dto.JobOfferDto;

public interface JobOfferRepo {
	void save(JobOfferDto jobOffer);

	Stream<JobOfferDto> findAllJobOffers();

	Optional<JobOfferDto> findJobOffer(Object jobId);

	Optional<JobOfferDto> findJobOfferByTitle(String title);

	Optional<JobApplicationDto> findJobApplication(Object jobId, String email);
}
