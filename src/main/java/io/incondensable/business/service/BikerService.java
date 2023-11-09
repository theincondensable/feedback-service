package io.incondensable.business.service;

import io.incondensable.business.exceptions.biker.BikerNotFoundWithId;
import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Biker;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.repository.BikerRepository;
import io.incondensable.global.security.vo.FeedbackUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class BikerService {

    private final UserService userService;
    private final BikerRepository bikerRepository;
    private final FeedbackService feedbackService;

    /**
     * <p>For the Logged-in User as a BIKER, use this method to get its information.</p>
     * <p>Since a Biker has a UserID, we can find the Logged-in Biker, using its Customer ID which is saved
     * in Security Context Holder.</p>
     *
     * @return the Biker who is currently Logged-in
     */
    public Biker getBikerByLoggedInUser() {
        FeedbackUserDetails loggedInBiker = (FeedbackUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User bikerUser = userService.getUserById(loggedInBiker.getId());

        return bikerRepository.findBikerByUser_Id(bikerUser.getId()).orElseThrow(
                () -> {
                    throw new BikerNotFoundWithId(bikerUser.getId());
                }
        );
    }

    /**
     * <p>Calculator of the Biker's Average Rating.</p>
     *
     * @param bikerId the Biker's ID to calculate the average its Ratings.
     * @return the average number calculated.
     */
    public double calculateAverageRatingOfBiker(Long bikerId) {
        return feedbackService.getAllFeedbacksOfBiker(bikerId).stream()
                .mapToDouble(
                        Feedback::getRating
                ).average()
                .getAsDouble();
    }

}
