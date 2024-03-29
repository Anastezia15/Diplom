package uni.diploma.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for the EventService.
 */
@RestControllerAdvice
public class EventServiceResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles all exceptions and returns an error response with an internal server error status.
     *
     * @param ex      the exception to handle.
     * @param request the current request.
     * @return a ResponseEntity containing the error details and status.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles EventNotFoundException and CategoryNotFoundException and returns an error response with a not found status.
     *
     * @param ex      the exception to handle.
     * @param request the current request.
     * @return a ResponseEntity containing the error details and status.
     */
    @ExceptionHandler({EventNotFoundException.class, CategoryNotFoundException.class})
    public final ResponseEntity<ErrorDetails> handleNotFoundExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles AlreadyExistsException and returns an error response with a conflict status.
     *
     * @param ex      the exception to handle.
     * @param request the current request.
     * @return a ResponseEntity containing the error details and status.
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleAlreadyExistsException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handles EventNotValidException and CategoryNotValidException and returns an error response with a bad request status.
     *
     * @param ex      the exception to handle.
     * @param request the current request.
     * @return a ResponseEntity containing the error details and status.
     */
    @ExceptionHandler({EventNotValidException.class, CategoryNotValidException.class})
    public final ResponseEntity<ErrorDetails> handleNotValidExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
