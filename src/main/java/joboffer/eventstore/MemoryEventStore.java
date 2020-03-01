package joboffer.eventstore;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MemoryEventStore implements EventStore {
	private ApplicationEventPublisher publisher;

	/**
	 * store should allow multi-threaded access
	 */
	private final ConcurrentHashMap<Object, List<Event>> store = new ConcurrentHashMap<>();

	@Autowired
	public MemoryEventStore(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public Stream<Event> load(Object aggregateId) {
		if (!store.containsKey(aggregateId)) {
			return Stream.empty();
		}
		return store.get(aggregateId).stream();
	}

	@Override
	public void store(List<Event> events) {
		if (!events.isEmpty()) {
			store.compute(events.get(0).getAggregateId(), (key, existing) -> {
				List<Event> values = existing;
				if (values == null) {
					values = new LinkedList<>();
				} else if (!values.isEmpty() && isStale(values, events)) {
					throw new OptimisticLockException();
				}

				values.addAll(events);
				events.forEach(e -> {
					publisher.publishEvent(e);
				});

				return values;
			});
		}
	}

	@Override
	public void delete(Object aggregateId) {
		store.remove(aggregateId);
	}

	static boolean isStale(List<Event> existing, List<Event> incoming) {
		int presentVersion = existing.get(existing.size() - 1).getVersion();
		int incomingFirstVersion = incoming.get(0).getVersion();
		return presentVersion >= incomingFirstVersion;
	}

}
