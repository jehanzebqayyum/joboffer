package joboffer.read.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import joboffer.read.persistence.dto.JobApplicationDto;
import joboffer.read.persistence.dto.JobOfferDto;
import joboffer.read.persistence.repo.JobOfferRepo;

@Service
public class QueryServiceImpl implements QueryService {
	private final JobOfferRepo repo;

	@Autowired
	public QueryServiceImpl(JobOfferRepo repo) {
		this.repo = repo;
	}

	@Override
	public Stream<JobOfferDto> findAllJobOffers() {
		return repo.findAllJobOffers();
	}

	@Override
	public Optional<JobOfferDto> findJobOffer(Object jobId) {
		return repo.findJobOffer(jobId);
	}

	@Override
	public Optional<JobApplicationDto> findJobApplication(Object jobId, String email) {
		return repo.findJobApplication(jobId, email);
	}

}
