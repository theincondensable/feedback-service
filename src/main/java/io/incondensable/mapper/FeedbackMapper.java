package io.incondensable.mapper;

import io.incondensable.business.model.client.User;
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
        User deliveree = feedback.getDelivery().getDeliveree();
        User biker = feedback.getDelivery().getBiker().getUser();

        return new SubmittedFeedbackResponseDto(
                (deliveree.getFirstname() + " " + deliveree.getLastname()),
                (biker.getFirstname() + " " + biker.getLastname()),
                feedback.getRating(),
                feedback.getComment()
        );
    }

    public FeedbackToManagerResponseDto entityToManagerDto(Feedback feedback) {
        User deliveree = feedback.getDelivery().getDeliveree();
        User biker = feedback.getDelivery().getBiker().getUser();

        return new FeedbackToManagerResponseDto(
                feedback.getId(),
                feedback.getDelivery().getId(),
                feedback.getBiker().getId(),
                (deliveree.getFirstname() + " " + deliveree.getLastname()),
                (biker.getFirstname() + " " + biker.getLastname()),
                feedback.getRating(),
                feedback.getComment(),
                Date.from(feedback.getCreatedOn())
        );
    }

}
