package joboffer.eventstore;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "stale data")
public class OptimisticLockException extends RuntimeException {
	private static final long serialVersionUID = -4242398176928706159L;
}
