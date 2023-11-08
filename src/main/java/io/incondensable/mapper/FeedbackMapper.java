package io.incondensable.mapper;

import io.incondensable.business.model.client.Customer;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.web.dto.feedback.response.SubmittedFeedbackResponseDto;

/**
 * @author abbas
 */
@Mapper
public class FeedbackMapper {

    public SubmittedFeedbackResponseDto entityToDto(Feedback feedback) {
        Customer customer = feedback.getDelivery().getCustomer();

        return new SubmittedFeedbackResponseDto(
                (customer.getFirstname() + customer.getLastname()),
                feedback.getDelivery().getBiker().getName(),
                feedback.getRating(),
                feedback.getComment()
        );
    }

}
