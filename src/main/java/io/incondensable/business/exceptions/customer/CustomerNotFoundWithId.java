package io.incondensable.business.exceptions.customer;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class CustomerNotFoundWithId extends BusinessException {

    public CustomerNotFoundWithId(Long customerId) {
        super(new ErrorDetails(
                "customer.not.found.with.given.email",
                HttpStatus.NOT_FOUND,
                customerId
        ));
    }

}
