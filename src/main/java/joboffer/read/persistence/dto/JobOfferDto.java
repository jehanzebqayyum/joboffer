package joboffer.read.persistence.dto;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

@Data
public class JobOfferDto {
	private Object id;
	private String title;
	private Date startDate;
	private final ConcurrentHashMap<String, JobApplicationDto> applications = new ConcurrentHashMap<>();

	public int getApplicationsCount() {
		return applications.size();
	}

	public void put(JobApplicationDto dto) {
		applications.put(dto.getEmail(), dto);
	}

	public JobApplicationDto get(String email) {
		return applications.get(email);
	}
}
