package io.incondensable.global.exception;

import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.StringMapMessage;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public final class ErrorDetails {
    private String message;
    private HttpStatus httpStatus;
    private Object[] args;
    private final MapMessage<StringMapMessage, String> errorDetail = new MapMessage<>();

    private ErrorDetails() {
    }

    public ErrorDetails(String message, HttpStatus httpStatus, Object... args) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.args = args;
        if (args != null)
            setErrorDetail(args);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object... args) {
        this.args = args;
    }

    public MapMessage<StringMapMessage, String> getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(Object... args) {
        for (int i = 0; i < args.length; i++) {
            this.errorDetail.put((String) args[i], String.valueOf(args[i]));
        }
    }
}
