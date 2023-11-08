package io.incondensable.business.exceptions.feedback;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class FeedbackInvalidCustomer extends BusinessException {

    public FeedbackInvalidCustomer(Long customerId, Long deliveryId) {
        super(new ErrorDetails(
                "feedback.must.be.submitted.by.proper.customer",
                HttpStatus.FORBIDDEN,
                customerId, deliveryId
        ));
    }

}
