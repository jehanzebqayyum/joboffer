package joboffer.command.api.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateJobDto {
	@NotNull
	private String title;

	@NotNull
	private Date startDate;
}
