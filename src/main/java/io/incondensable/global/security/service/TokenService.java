package io.incondensable.global.security.service;

import io.incondensable.business.model.auth.Token;
import io.incondensable.business.model.client.User;
import io.incondensable.business.repository.TokenRepository;
import io.incondensable.global.security.exceptions.ExpiredJwtToken;
import io.incondensable.global.security.exceptions.UserTokenNotFound;
import io.incondensable.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public Token getTokenByUserId(Long userId) {
        return tokenRepository.findTokenByUserId(userId).orElseThrow(
                () -> {
                    throw new UserTokenNotFound(userId);
                }
        );
    }

    /**
     * <p>The method is used in JwtFilter in which the requests are intercepted and must be having the JWT Token.</p>
     * <p>this method enables checking if the User's Token is still Valid in terms of its ActiveTime.</p>
     *
     * @param userId the User ID which their JWT Token should exist.
     */
    public void validateTokenExpiration(Long userId) {
        Token token = getTokenByUserId(userId);

        Instant now = Instant.now();
        if (token.getActiveTime().isBefore(now)) {
            tokenRepository.delete(token);
            throw new ExpiredJwtToken(userId);
        }
        token.setActiveTime(Instant.from(now.plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)));
        tokenRepository.save(token);

    }

    /**
     * <p>On the Login request, The User might have a Token assigned to them already.</p>
     * <p>if the User already has a Token it returns the previous Token and its ActiveTime increases.</p>
     * <p>if the User does not have a Token yet, means that the User whether is New or they logged out from
     * their account, so that a JWT Token is Generated for them.</p>
     *
     * @param user the User that attempted to Log in.
     * @return the Jwt token
     */
    public Token generateJwtTokenOnLogin(User user) {
        return tokenRepository.findTokenByUserId(user.getId()).map(
                token -> { // The User already has a Token.
                    token.setActiveTime(Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)));
                    tokenRepository.save(token);
                    return token;
                }).orElseGet(() -> { // The User does not have a Token yet.
                    return tokenRepository.save(
                            Token.builder()
                                    .user(user)
                                    .token(jwtUtil.generateJwtToken(user.getEmail()))
                                    .activeTime(Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)))
                                    .build()
                    );
                }
        );
    }

    /**
     * <p>First sets the Token's User to NULL, to prevent foreign key related issues.</p>
     * <p>Finally deletes the Token from the Database.</p>
     *
     * @param userId the User to log out
     */
    public void deleteTokenOnLogout(long userId) {
        Token token = getTokenByUserId(userId);

        token.setUser(null);
        tokenRepository.delete(token);
    }

}
