package io.incondensable.business.service;

import io.incondensable.business.exceptions.feedback.FeedbackInvalidUser;
import io.incondensable.business.exceptions.feedback.FeedbackSubmittedForDelivery;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.FeedbackRepository;
import io.incondensable.global.security.vo.FeedbackUserDetails;
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

    private final DeliveryService deliveryService;
    private final FeedbackRepository feedbackRepository;
    private final NotificationService notificationService;

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

        FeedbackUserDetails loggedInCustomer = (FeedbackUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!loggedInCustomer.getId().equals(delivery.getDeliveree().getId()))
            throw new FeedbackInvalidUser(delivery.getDeliveree().getId(), delivery.getId());

        Feedback feedback = Feedback.builder()
                .delivery(delivery)
                .biker(delivery.getBiker())
                .rating(req.getRating())
                .comment(req.getComment())
                .build();

        feedbackRepository.save(feedback);

        notificationService.notifyByPhoneNumber(feedback.getBiker().getUser().getPhoneNumber());

        return feedback;
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
