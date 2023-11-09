package io.incondensable.business.service.model.Delivery;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Biker;
import io.incondensable.business.model.domain.Delivery;
import io.incondensable.util.FeedbackTestUtil;

import java.time.Instant;
import java.util.List;

/**
 * @author abbas
 */
public class DeliveryDummy {

    private DeliveryDummy() {

    }

    public static Delivery create(Long id, User deliveree) {
        return new Delivery(
                id,
                new Biker(),
                deliveree,
                Instant.now()
        );
    }

    public static List<Delivery> createDeliveriesWithDelivereeId(Long id) {
        User u1 = FeedbackTestUtil.CUSTOMER_USER;
        u1.setId(id);
        User u2 = FeedbackTestUtil.CUSTOMER_USER;
        u2.setId(20L);
        User u3 = FeedbackTestUtil.CUSTOMER_USER;
        u3.setId(30L);

        return List.of(
                create(1L, u1),
                create(2L, u1),
                create(3L, u3),
                create(4L, u2),
                create(5L, u3),
                create(6L, u1)
        );
    }

}
