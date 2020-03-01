package joboffer.misc;

/**
 * Interface to unique ids in the system
 */
public interface IdGenerator<T> {
	T getNextId();
}
