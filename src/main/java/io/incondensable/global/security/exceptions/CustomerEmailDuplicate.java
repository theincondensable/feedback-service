package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class CustomerEmailDuplicate extends BusinessException {

    public CustomerEmailDuplicate(String email) {
        super(new ErrorDetails(
                "given.email.already.exists",
                HttpStatus.CONFLICT,
                email
        ));
    }

}
