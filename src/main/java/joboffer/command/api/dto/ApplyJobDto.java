package joboffer.command.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplyJobDto {

	@NotNull
	@Email
	private String email;

	@NotNull
	private String resume;
}
