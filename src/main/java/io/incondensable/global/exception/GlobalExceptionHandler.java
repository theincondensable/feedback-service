package io.incondensable.global.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author abbas
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> businessExceptionHandler(BusinessException exception) {
        return ResponseEntity
                .status(exception.getErrorDetails().getHttpStatus())
                .body(messageSource.getMessage(
                        exception.getErrorDetails().getMessage(),
                        exception.getErrorDetails().getArgs(),
                        Locale.getDefault())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDetails>> validationException(MethodArgumentNotValidException ex) {
        List<ErrorDetails> errorDetail = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    ErrorDetails errorDetails = new ErrorDetails(
                            error.getDefaultMessage(),
                            HttpStatus.NOT_ACCEPTABLE,
                            error.getArguments()
                    );
                    errorDetail.add(errorDetails);
                }
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetail);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseEntity> accessDeniedException() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("This User can't access this API.")
                );
    }

}
