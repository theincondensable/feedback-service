package io.incondensable.mapper;

import io.incondensable.business.model.client.Customer;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.web.dto.feedback.response.FeedbackToManagerResponseDto;
import io.incondensable.web.dto.feedback.response.SubmittedFeedbackResponseDto;

import java.util.Date;

/**
 * @author abbas
 */
@Mapper
public class FeedbackMapper {

    public SubmittedFeedbackResponseDto entityToDto(Feedback feedback) {
        Customer customer = feedback.getDelivery().getCustomer();

        return new SubmittedFeedbackResponseDto(
                (customer.getFirstname() + " " + customer.getLastname()),
                feedback.getDelivery().getBiker().getName(),
                feedback.getRating(),
                feedback.getComment()
        );
    }

    public FeedbackToManagerResponseDto entityToManagerDto(Feedback feedback) {
        Customer customer = feedback.getDelivery().getCustomer();

        return new FeedbackToManagerResponseDto(
                feedback.getId(),
                feedback.getDelivery().getId(),
                feedback.getBiker().getId(),
                (customer.getFirstname() + " " + customer.getLastname()),
                feedback.getDelivery().getBiker().getName(),
                feedback.getRating(),
                feedback.getComment(),
                Date.from(feedback.getCreatedOn())
        );
    }

}
