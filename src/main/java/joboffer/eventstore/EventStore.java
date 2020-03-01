package joboffer.eventstore;

import java.util.List;
import java.util.stream.Stream;

/**
 * Stores events (in db/persistent store) and also publishes the stored event to
 * subscribers, all within a single transaction/atomic.
 *
 */
public interface EventStore {
	Stream<Event> load(Object aggregateId);

	/**
	 * Store and publish all events. All the events should belong to single
	 * aggregate. (ACID)
	 */
	void store(List<Event> events);

	/**
	 * Delete all events belonging to an aggregate id. (ACID).
	 */
	void delete(Object aggregateId);
}
