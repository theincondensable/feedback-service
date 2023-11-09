package io.incondensable.business.exceptions.user;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class UserNotFoundWithId extends BusinessException {

    public UserNotFoundWithId(Long customerId) {
        super(new ErrorDetails(
                "user.not.found.with.given.id",
                HttpStatus.NOT_FOUND,
                customerId
        ));
    }

}
