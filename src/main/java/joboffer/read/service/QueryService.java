package joboffer.read.service;

import java.util.Optional;
import java.util.stream.Stream;

import joboffer.read.persistence.dto.JobApplicationDto;
import joboffer.read.persistence.dto.JobOfferDto;

public interface QueryService {
	Stream<JobOfferDto> findAllJobOffers();

	Optional<JobOfferDto> findJobOffer(Object jobId);

	Optional<JobApplicationDto> findJobApplication(Object jobId, String email);
}
