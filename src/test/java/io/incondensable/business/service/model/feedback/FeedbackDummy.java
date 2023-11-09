package io.incondensable.business.service.model.feedback;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.business.model.domain.Feedback;
import io.incondensable.business.service.model.biker.BikerDummy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author abbas
 */
public class FeedbackDummy {

    private FeedbackDummy() {

    }

    public static Feedback createFeedbackEmptyDelivery(Long id, User user, byte rating, Instant createDate) {
        Delivery delivery = new Delivery();
        delivery.setCreatedOn(createDate);

        return new Feedback(
                id,
                delivery,
                BikerDummy.createBiker(user),
                rating,
                "",
                Instant.now()
        );
    }

    public static List<Feedback> createList(User bikerUser) {
        return List.of(
                FeedbackDummy.createFeedbackEmptyDelivery(1L, bikerUser, (byte) 4, Instant.now().minus(1, ChronoUnit.MINUTES)),
                FeedbackDummy.createFeedbackEmptyDelivery(2L, bikerUser, (byte) 2, Instant.now().minus(20, ChronoUnit.MINUTES)),
                FeedbackDummy.createFeedbackEmptyDelivery(3L, bikerUser, (byte) 5, Instant.now().minus(3, ChronoUnit.MINUTES)),
                FeedbackDummy.createFeedbackEmptyDelivery(4L, bikerUser, (byte) 1, Instant.now().minus(4, ChronoUnit.MINUTES)),
                FeedbackDummy.createFeedbackEmptyDelivery(5L, bikerUser, (byte) 3, Instant.now().minus(5, ChronoUnit.MINUTES)),
                FeedbackDummy.createFeedbackEmptyDelivery(6L, bikerUser, (byte) 5, Instant.now().minus(6, ChronoUnit.MINUTES))
        );
    }

}
