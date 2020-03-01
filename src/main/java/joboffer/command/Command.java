package joboffer.command;

/**
 * marker interface for commands
 *
 */
public interface Command<T> {
	Object getAggregateId(); // make sure command is targeted to single aggregate
}
