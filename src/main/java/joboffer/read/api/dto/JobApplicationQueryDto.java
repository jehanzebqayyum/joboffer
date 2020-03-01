package joboffer.read.api.dto;

import joboffer.read.persistence.dto.JobApplicationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobApplicationQueryDto {
	private String email;
	private String resume;

	public JobApplicationQueryDto(JobApplicationDto dto) {
		this(dto.getEmail(), dto.getResume());
	}
}
