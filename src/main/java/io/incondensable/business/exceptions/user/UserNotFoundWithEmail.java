package io.incondensable.business.exceptions.user;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class UserNotFoundWithEmail extends BusinessException {

    public UserNotFoundWithEmail(String email) {
        super(new ErrorDetails(
                "user.not.found.with.given.email",
                HttpStatus.NOT_FOUND,
                email
        ));
    }

}
