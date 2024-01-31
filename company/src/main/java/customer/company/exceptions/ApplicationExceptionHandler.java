package customer.company.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)

public class ApplicationExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(
            final ApplicationException exception
    ) {
        log.error(
                String.format("error message: %s", exception.getMessage()),
                exception
        );
        var response = new ApiErrorResponse(
                exception.getMessage(),
                exception.getHttpStatus().value(),
                exception.getHttpStatus().name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(
            final Exception exception
    ) {
        log.error(
                String.format("error message: %s", exception.getMessage()),
                exception
        );
        var response = new ApiErrorResponse(
                "INTERNAL_ERROR",
                500,
                "INTERNAL_ERROR",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
