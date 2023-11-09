package io.incondensable.business.service.model.biker;

import io.incondensable.business.model.client.User;
import io.incondensable.business.model.domain.Biker;

import java.time.Instant;
import java.util.Set;

/**
 * @author abbas
 */
public class BikerDummy {

    private BikerDummy() {

    }

    public static Biker createBiker(User user) {
        return new Biker(
                user.getId() + 5,
                Set.of(),
                user,
                Instant.now(),
                null
        );
    }

}
