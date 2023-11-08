package io.incondensable.global.exception;

/**
 * @author abbas
 */
public class BusinessException extends RuntimeException {

    private ErrorDetails errorDetails;

    private BusinessException() {
    }

    public BusinessException(ErrorDetails errorDetails) {
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
    }

    public BusinessException(BusinessException ex) {
        super(ex.getErrorDetails().getMessage());
        this.errorDetails = ex.getErrorDetails();
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

}
