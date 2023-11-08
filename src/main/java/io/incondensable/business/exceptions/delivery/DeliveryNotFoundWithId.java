package io.incondensable.business.exceptions.delivery;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class DeliveryNotFoundWithId extends BusinessException {
    public DeliveryNotFoundWithId(Long deliveryId) {
        super(new ErrorDetails(
                "delivery.not.found.with.given.id",
                HttpStatus.NOT_FOUND,
                deliveryId
        ));
    }
}
