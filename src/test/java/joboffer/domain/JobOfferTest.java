package joboffer.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import joboffer.command.ApplyJob;
import joboffer.command.CreateJob;
import joboffer.command.ProgressJobApplication;
import joboffer.domain.event.JobApplicationProgressed;
import joboffer.domain.event.JobApplied;
import joboffer.domain.event.JobOfferCreated;
import joboffer.eventstore.Event;

public class JobOfferTest {

	@Test
	public void shouldCreateApplyProgress() {
		CreateJob cmd = new CreateJob(1, "title", new Date());
		JobOfferCreated expected = new JobOfferCreated(cmd.getAggregateId(), 1, cmd.getTitle(), cmd.getStartDate());

		JobOffer job = new JobOffer();
		List<Event> actuals = job.create(cmd);
		

		assertThat(actuals.isEmpty(), equalTo(false));
		assertThat(actuals.get(0), equalTo(expected));

		// apply
		ApplyJob acmd = new ApplyJob(cmd.getAggregateId(), "j@j.com", "resume");
		JobApplied aexpected = new JobApplied(cmd.getAggregateId(), 2, acmd.getEmail(), acmd.getResume());
		actuals = job.applyJob(acmd);

		assertThat(actuals.isEmpty(), equalTo(false));
		assertThat(actuals.get(0), equalTo(aexpected));

		// progress
		ProgressJobApplication pcmd = new ProgressJobApplication(cmd.getAggregateId(), acmd.getEmail(),
				JobApplicationStatus.INVITED);
		JobApplicationProgressed pexpected = new JobApplicationProgressed(cmd.getAggregateId(), 3, acmd.getEmail(),
				pcmd.getNextStatus());
		actuals = job.progress(pcmd);
		assertThat(actuals.isEmpty(), equalTo(false));
		assertThat(actuals.get(0), equalTo(pexpected));

	}

}
