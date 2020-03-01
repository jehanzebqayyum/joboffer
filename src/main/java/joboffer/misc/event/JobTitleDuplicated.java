package joboffer.misc.event;

import lombok.Data;

@Data
public class JobTitleDuplicated {
	private final String title;
	private final Object duplicatedJobId;
}
