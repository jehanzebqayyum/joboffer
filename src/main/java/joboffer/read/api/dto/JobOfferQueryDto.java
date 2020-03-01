package joboffer.read.api.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import joboffer.read.persistence.dto.JobOfferDto;
import lombok.Data;

@Data
public class JobOfferQueryDto {
	private Object id;
	private String title;
	private Date startDate;

	private List<JobApplicationQueryDto> applications = new LinkedList<>();

	public JobOfferQueryDto(Object id, String title, Date startDate) {
		this.id = id;
		this.title = title;
		this.startDate = startDate;
	}

	public JobOfferQueryDto(JobOfferDto dto) {
		this(dto.getId(), dto.getTitle(), dto.getStartDate());
		this.applications = dto.getApplications().values().stream().map(JobApplicationQueryDto::new)
				.collect(Collectors.toList());
	}

	public int getApplicationsCount() {
		return applications.size();
	}
}
