package io.incondensable.business.service.model.Delivery;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Biker;
import io.incondensable.business.model.domain.Delivery;

import java.time.Instant;

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

}
