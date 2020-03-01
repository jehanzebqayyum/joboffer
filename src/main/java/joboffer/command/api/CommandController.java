package joboffer.command.api;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import joboffer.command.ApplyJob;
import joboffer.command.CreateJob;
import joboffer.command.ProgressJobApplication;
import joboffer.command.api.dto.ApplyJobDto;
import joboffer.command.api.dto.CreateJobDto;
import joboffer.command.api.dto.ProgressJobApplicationDto;
import joboffer.command.handler.ApplyJobHandler;
import joboffer.command.handler.CreateJobHandler;
import joboffer.command.handler.ProgressJobApplicationHandler;
import joboffer.misc.IdGenerator;

@RestController
@RequestMapping("/api/")
public class CommandController {
	private final CreateJobHandler createJobHandler;
	private final ApplyJobHandler applyJobHandler;
	private final ProgressJobApplicationHandler progressJobAppHandler;
	private final IdGenerator<UUID> idGenerator;

	@Autowired
	public CommandController(CreateJobHandler createJobHandler, ApplyJobHandler applyJobHandler,
			ProgressJobApplicationHandler progressJobAppHandler, IdGenerator<UUID> idGenerator) {
		this.createJobHandler = createJobHandler;
		this.applyJobHandler = applyJobHandler;
		this.progressJobAppHandler = progressJobAppHandler;
		this.idGenerator = idGenerator;
	}

	@PutMapping(path = "/job", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createJob(@Valid @RequestBody CreateJobDto dto) {
		CreateJob cmd = new CreateJob(idGenerator.getNextId(), dto.getTitle(), dto.getStartDate());
		createJobHandler.handle(cmd);
		return ResponseEntity.created(URI.create("/api/query/job/" + cmd.getAggregateId()))
				.contentType(MediaType.TEXT_PLAIN).build();
	}

	@PutMapping(path = "/job/{jobId}/application", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> applyJob(@Valid @RequestBody ApplyJobDto dto,
			@NotNull @PathVariable("jobId") UUID jobId) {
		ApplyJob cmd = new ApplyJob(jobId, dto.getEmail(), dto.getResume());
		applyJobHandler.handle(cmd);
		return ResponseEntity.ok().build();
	}

	@PostMapping(path = "/job/{jobId}/application/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> progressJobApplication(@Valid @RequestBody ProgressJobApplicationDto dto,
			@NotNull @PathVariable("jobId") UUID jobId) {
		ProgressJobApplication cmd = new ProgressJobApplication(jobId, dto.getEmail(), dto.getNextStatus());
		progressJobAppHandler.handle(cmd);

		String uri = String.format("/api/query/job/%s/application?email=%s", jobId, dto.getEmail());
		return ResponseEntity.created(URI.create(uri)).build();
	}
}
