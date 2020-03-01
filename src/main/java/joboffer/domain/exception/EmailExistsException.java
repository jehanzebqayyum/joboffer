package joboffer.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates job application with same email already exists for a particular job
 * offer
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "job application with same email already exists")
public class EmailExistsException extends RuntimeException {
}
