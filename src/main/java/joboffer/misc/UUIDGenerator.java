package joboffer.misc;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * Generate id from UUID. Ideal implementation of id generation should guarantee
 * uniqueness e.g. using database sequence.
 *
 */
@Component
public class UUIDGenerator implements IdGenerator<UUID> {

	@Override
	public UUID getNextId() {
		return UUID.randomUUID();
	}

}
