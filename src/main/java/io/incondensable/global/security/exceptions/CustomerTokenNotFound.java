package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class CustomerTokenNotFound extends BusinessException {

    public CustomerTokenNotFound(Long customerId) {
        super(new ErrorDetails(
                "customer.token.not.found",
                HttpStatus.FORBIDDEN,
                customerId
        ));
    }

}
