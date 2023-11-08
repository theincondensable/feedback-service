package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;

/**
 * @author abbas
 */
public class CustomerNotAuthorized extends BusinessException {

    public CustomerNotAuthorized(ErrorDetails errorDetails) {
        super(errorDetails);
    }

}
