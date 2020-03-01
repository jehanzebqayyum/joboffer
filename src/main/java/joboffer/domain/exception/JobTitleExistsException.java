package joboffer.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "job title already exists")
public class JobTitleExistsException extends RuntimeException {
}
