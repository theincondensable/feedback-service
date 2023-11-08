package io.incondensable.business.service;

import io.incondensable.business.exceptions.feedback.FeedbackInvalidCustomer;
import io.incondensable.business.exceptions.feedback.FeedbackSubmittedForDelivery;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.FeedbackRepository;
import io.incondensable.global.security.vo.CustomerDetails;
import io.incondensable.web.dto.feedback.request.FeedbackRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * <p>The first validation is that if the Delivery has already a Feedback submitted on, an Exception is thrown.</p>
     * <p>The second validation is that the Customer who is submitting the Feedback, must be one who the Delivery
     * is delivered to.</p>
     *
     * @param req the incoming Feedback request for a specific Delivery.
     * @return the Feedback Entity
     */
    public Feedback submitFeedback(FeedbackRequestDto req) {
        feedbackRepository.findFeedbackByDelivery_Id(req.getDeliveryId()).ifPresent(
                feedback -> {
                    throw new FeedbackSubmittedForDelivery(req.getDeliveryId());
                });

        Delivery delivery = deliveryService.getDeliveryById(req.getDeliveryId());

        CustomerDetails loggedInCustomer = (CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!loggedInCustomer.getId().equals(delivery.getCustomer().getId()))
            throw new FeedbackInvalidCustomer(delivery.getCustomer().getId(), delivery.getId());

        Feedback feedback = Feedback.builder()
                .delivery(delivery)
                .biker(delivery.getBiker())
                .rating(req.getRating())
                .comment(req.getComment())
                .build();

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacksSortedByDate() {
        return feedbackRepository.findAllSortedByDeliveryCreatedDate();
    }

    public List<Feedback> getAllFeedbacksOfBiker(Long bikerId) {
        return feedbackRepository.findAllByBiker_Id(bikerId);
    }

    public List<Feedback> getAllFeedbacksOfRating(Byte rating) {
        return feedbackRepository.findAllByRating(rating);
    }

}
