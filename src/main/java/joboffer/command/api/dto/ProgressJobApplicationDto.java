package joboffer.command.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import joboffer.domain.JobApplicationStatus;
import lombok.Data;

@Data
public class ProgressJobApplicationDto {
	@NotNull
	@Email
	private String email;

	@NotNull
	private JobApplicationStatus nextStatus;
}
