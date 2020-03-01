package joboffer.read.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import joboffer.read.api.dto.JobApplicationQueryDto;
import joboffer.read.api.dto.JobOfferQueryDto;
import joboffer.read.service.QueryService;

@RestController()
@RequestMapping("/api/view/")
public class QueryController {

	private final QueryService queryService;

	@Autowired
	public QueryController(QueryService queryService) {
		this.queryService = queryService;
	}

	@GetMapping("/job/{id}")
	public ResponseEntity<JobOfferQueryDto> findJobOffer(@NotNull @PathVariable UUID id, @RequestParam boolean full) {
		return queryService.findJobOffer(id).map(JobOfferQueryDto::new).map(j -> ResponseEntity.ok(j))
				.orElse(ResponseEntity.noContent().build());
	}

	// TODO: use pagination
	@GetMapping("/job/")
	public ResponseEntity<List<JobOfferQueryDto>> findAllJobOffers(@RequestParam boolean full) {
		List<JobOfferQueryDto> results = queryService.findAllJobOffers().map(JobOfferQueryDto::new)
				.collect(Collectors.toList());
		if (results.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(results);
	}

	@GetMapping("/job/{id}/application")
	public ResponseEntity<List<JobApplicationQueryDto>> findJobApplications(@NotNull @PathVariable UUID id,
			@RequestParam("email") String email) {
		// TODO
		return null;
	}

}
