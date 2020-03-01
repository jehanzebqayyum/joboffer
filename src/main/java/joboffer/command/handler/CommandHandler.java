package joboffer.command.handler;

public interface CommandHandler<Command> {
	/**
	 * Transaction boundary.
	 */
	void handle(Command c);
}
