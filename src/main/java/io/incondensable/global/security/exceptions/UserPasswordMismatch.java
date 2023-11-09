package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class UserPasswordMismatch extends BusinessException {

    public UserPasswordMismatch(String email) {
        super(new ErrorDetails(
                "entered.password.mismatch",
                HttpStatus.FORBIDDEN,
                email
        ));
    }

}
