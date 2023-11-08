package io.incondensable.global.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<ErrorDetails> businessExceptionHandler(BusinessException exception) {
        return ResponseEntity
                .status(exception.getErrorDetails().getHttpStatus())
                .body(exception.getErrorDetails());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDetails>> validationException(MethodArgumentNotValidException ex) {
        List<ErrorDetails> errorCodes = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    ErrorDetails errorDetails = new ErrorDetails(
                            error.getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE, error.getArguments()
                    );
                    errorCodes.add(errorDetails);
                }
        );
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorCodes);
    }

}
