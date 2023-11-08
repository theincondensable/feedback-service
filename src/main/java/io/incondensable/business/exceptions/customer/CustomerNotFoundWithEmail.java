package io.incondensable.business.exceptions.customer;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class CustomerNotFoundWithEmail extends BusinessException {

    public CustomerNotFoundWithEmail(String email) {
        super(new ErrorDetails(
                "customer.not.found.with.given.email",
                HttpStatus.NOT_FOUND,
                email
        ));
    }

}
