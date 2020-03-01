package joboffer.eventstore;

import java.io.Serializable;
import java.util.Date;

public interface Event extends Serializable {
	/**
	 * an event belongs to an aggregate root
	 */
	Object getAggregateId();

	/**
	 * version of aggregate root to implement concurrency
	 */
	int getVersion();

	/**
	 * event timestamp to support temporal queries in eventstore
	 */
	Date getTimestamp();

}
