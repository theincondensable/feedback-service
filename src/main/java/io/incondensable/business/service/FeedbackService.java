package io.incondensable.business.service;

import io.incondensable.business.exceptions.feedback.FeedbackSubmittedForDelivery;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.FeedbackRepository;
import io.incondensable.web.dto.feedback.request.FeedbackRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DeliveryService deliveryService;

    public Feedback submitFeedback(FeedbackRequestDto req) {
        feedbackRepository.findFeedbackByDelivery_Id(req.getDeliveryId()).ifPresent(
                feedback -> {
                    throw new FeedbackSubmittedForDelivery(req.getDeliveryId());
                }
        );

        Delivery delivery = deliveryService.getDeliveryById(req.getDeliveryId());

        Feedback feedback = Feedback.builder()
                .delivery(delivery)
                .biker(delivery.getBiker())
                .rating(req.getRating())
                .comment(req.getComment())
                .build();

        return feedbackRepository.save(feedback);
    }

}
