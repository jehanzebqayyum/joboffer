package joboffer.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import joboffer.command.ProgressJobApplication;
import joboffer.domain.JobOffer;
import joboffer.domain.exception.EntityNotFoundException;
import joboffer.eventstore.EventStore;

@Service
public class ProgressJobApplicationHandler implements CommandHandler<ProgressJobApplication> {
	@Autowired
	private EventStore store;

	@Override
	public void handle(ProgressJobApplication cmd) {
		JobOffer root = new JobOffer(store.load(cmd.getAggregateId()));
		if (!root.isCreated()) {
			throw new EntityNotFoundException();
		}
		store.store(root.progress(cmd));
	}

}
