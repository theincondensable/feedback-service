package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class UserTokenNotFound extends BusinessException {

    public UserTokenNotFound(Long customerId) {
        super(new ErrorDetails(
                "user.token.not.found",
                HttpStatus.FORBIDDEN,
                String.valueOf(customerId)
        ));
    }

}
