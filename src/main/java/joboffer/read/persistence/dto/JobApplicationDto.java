package joboffer.read.persistence.dto;

import lombok.Data;

@Data
public class JobApplicationDto {
	private String email;
	private String resume;
	private String status;
}
