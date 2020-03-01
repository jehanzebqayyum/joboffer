package joboffer.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import joboffer.command.CreateJob;
import joboffer.domain.JobOffer;
import joboffer.domain.exception.JobTitleExistsException;
import joboffer.eventstore.EventStore;
import joboffer.read.service.QueryService;

@Service
public class CreateJobHandler implements CommandHandler<CreateJob> {
	private EventStore store;

	private QueryService queryService;

	@Autowired
	public CreateJobHandler(EventStore store, QueryService queryService) {
		this.store = store;
		this.queryService = queryService;
	}

	public void handle(CreateJob cmd) {
		// enforce invariant that job title is unique. can fail in concurrent access
		if (queryService.findJobOffer(cmd.getAggregateId()).isPresent()) {
			throw new JobTitleExistsException();
		}

		JobOffer root = new JobOffer();
		store.store(root.create(cmd));
	}

}
