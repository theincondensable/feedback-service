package io.incondensable.business.service;

import io.incondensable.business.exceptions.biker.BikerNotFoundWithId;
import io.incondensable.business.model.domain.Biker;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.BikerRepository;
import io.incondensable.global.security.vo.CustomerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class BikerService {

    private final BikerRepository bikerRepository;
    private final FeedbackService feedbackService;

    public Biker getBikerById(Long bikerId) {
        return bikerRepository.findById(bikerId).orElseThrow(() -> {
            throw new BikerNotFoundWithId(bikerId);
        });
    }

    public Biker getBikerByLoggedInCustomer() {
        CustomerDetails loggedInCustomer = (CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getBikerById(loggedInCustomer.getId());
    }

    public double calculateAverageRatingOfBiker(Long bikerId) {
        return feedbackService.getAllFeedbacksOfBiker(bikerId).stream()
                .mapToDouble(
                        Feedback::getRating
                ).average()
                .getAsDouble();
    }

}
