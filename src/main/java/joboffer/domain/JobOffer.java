package joboffer.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import joboffer.command.ApplyJob;
import joboffer.command.CreateJob;
import joboffer.command.ProgressJobApplication;
import joboffer.domain.event.DomainEvent;
import joboffer.domain.event.JobApplicationProgressed;
import joboffer.domain.event.JobApplied;
import joboffer.domain.event.JobOfferCreated;
import joboffer.domain.exception.EmailExistsException;
import joboffer.eventstore.Event;
import lombok.EqualsAndHashCode;

/**
 * Aggregate Root
 *
 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JobOffer {

	/**
	 * using system generated id, instead of unique title which may change
	 */
	@EqualsAndHashCode.Include
	private Object id;

	/**
	 * title uniqueness is an invariant which has to be maintained by application
	 * services/command handlers
	 */
	private String title;

	/**
	 * date when job starts
	 */
	private Date startDate;

	private int version = 0;

	private boolean created = false;

	private final boolean existing;

	private final Map<String, JobApplication> jobApplications = new HashMap<>();

	public Object getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public boolean isCreated() {
		return created;
	}

	public JobOffer() {
		existing = false;
	}

	public JobOffer(Stream<Event> stream) {
		existing = true;
		stream.filter(e -> e instanceof DomainEvent).forEach(e -> ((DomainEvent) e).apply(this));
	}

	public List<Event> create(CreateJob cmd) {
		JobOfferCreated event = new JobOfferCreated(cmd.getAggregateId(), ++version, cmd.getTitle(),
				cmd.getStartDate());
		this.apply(event);
		return Arrays.asList(event);
	}

	/**
	 * Apply for a job. All access to job application is through aggregate root to
	 * maintain invariants.
	 * 
	 */
	public List<Event> applyJob(ApplyJob cmd) {
		if (jobApplications.containsKey(cmd.getEmail())) {
			throw new EmailExistsException();
		}

		JobApplied event = new JobApplied(cmd.getAggregateId(), ++version, cmd.getEmail(), cmd.getResume());
		this.apply(event);
		return Arrays.asList(event);
	}

	public List<Event> progress(ProgressJobApplication cmd) {
		JobApplicationProgressed event = new JobApplicationProgressed(cmd.getAggregateId(), ++version, cmd.getEmail(),
				cmd.getNextStatus());
		this.apply(event);
		return Arrays.asList(event);
	}

	public void apply(JobOfferCreated event) {
		this.id = event.getAggregateId();
		this.title = event.getTitle();
		this.startDate = event.getStartDate();
		this.created = true;
	}

	public void apply(JobApplied event) {
		// on loading exist aggregate, resume is not loaded for efficiency purposes
		String resume = null;
		if (!existing) {
			resume = event.getResume();
		}

		JobApplication app = new JobApplication(event.getEmail(), resume);
		this.jobApplications.put(event.getEmail(), app);
	}

	public void apply(JobApplicationProgressed event) {
		JobApplication app = jobApplications.get(event.getEmail());
		app.progress(event.getCurrentStatus());
	}

}
