package io.incondensable.business.service.model.token;

import io.incondensable.business.model.auth.Token;
import io.incondensable.business.model.client.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author abbas
 */
public class TokenDummy {

    private TokenDummy() {

    }

    public static Token createExpiredToken() {
        return new Token(1L, new User(), "KJNDF*@INFG(@ih", Instant.now().minus(10, ChronoUnit.SECONDS));
    }

    public static Token createActiveToken() {
        return new Token(1L, new User(), "KJNDF*@INFG(@ih", Instant.now().plus(10, ChronoUnit.SECONDS));
    }

}
