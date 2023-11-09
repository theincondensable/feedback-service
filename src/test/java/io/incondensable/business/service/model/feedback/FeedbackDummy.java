package io.incondensable.business.service.model.feedback;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Biker;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.service.model.biker.BikerDummy;

import java.time.Instant;
import java.util.List;

/**
 * @author abbas
 */
public class FeedbackDummy {

    private FeedbackDummy() {

    }

    public static Feedback createFeedbackEmptyDelivery(Long id, User user, byte rating) {
        return new Feedback(
                id,
                new Delivery(),
                BikerDummy.createBiker(user),
                rating,
                "",
                Instant.now()
        );
    }

    public static List<Feedback> createList(User bikerUser) {
        return List.of(
                FeedbackDummy.createFeedbackEmptyDelivery(1L, bikerUser, (byte) 4),
                FeedbackDummy.createFeedbackEmptyDelivery(2L, bikerUser, (byte) 2),
                FeedbackDummy.createFeedbackEmptyDelivery(3L, bikerUser, (byte) 5),
                FeedbackDummy.createFeedbackEmptyDelivery(4L, bikerUser, (byte) 1),
                FeedbackDummy.createFeedbackEmptyDelivery(5L, bikerUser, (byte) 3),
                FeedbackDummy.createFeedbackEmptyDelivery(6L, bikerUser, (byte) 5)
        );
    }

}
