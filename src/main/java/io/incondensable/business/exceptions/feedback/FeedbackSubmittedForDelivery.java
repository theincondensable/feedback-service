package io.incondensable.business.exceptions.feedback;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class FeedbackSubmittedForDelivery extends BusinessException {

    public FeedbackSubmittedForDelivery(Long deliveryId) {
        super(new ErrorDetails(
                "feedback.already.submitted.for.delivery",
                HttpStatus.CONFLICT,
                deliveryId
        ));
    }

}
