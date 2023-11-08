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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setErrorDetail(Object... args) {
        for (Object arg : args) {
            this.errorDetail.put(String.valueOf(arg), String.valueOf(arg));
        }
    }
}
