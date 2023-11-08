package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class EmailDuplicate extends BusinessException {

    public EmailDuplicate(String email) {
        super(new ErrorDetails(
                "given.email.already.exists",
                HttpStatus.FORBIDDEN,
                email
        ));
    }

}
