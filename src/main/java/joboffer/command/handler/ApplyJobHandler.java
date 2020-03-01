package joboffer.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import joboffer.command.ApplyJob;
import joboffer.domain.JobOffer;
import joboffer.domain.exception.EntityNotFoundException;
import joboffer.eventstore.EventStore;

@Service
public class ApplyJobHandler implements CommandHandler<ApplyJob> {
	@Autowired
	private EventStore store;

	@Override
	public void handle(ApplyJob cmd) {
		JobOffer root = new JobOffer(store.load(cmd.getAggregateId()));
		if (!root.isCreated()) {
			throw new EntityNotFoundException();
		}
		store.store(root.applyJob(cmd));
	}

}
