package io.incondensable.global.security.service;

import io.incondensable.business.model.auth.Token;
import io.incondensable.business.model.client.Customer;
import io.incondensable.business.repository.TokenRepository;
import io.incondensable.global.security.exceptions.CustomerTokenNotFound;
import io.incondensable.global.security.exceptions.ExpiredJwtToken;
import io.incondensable.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    /**
     * <p>The method is used in JwtFilter in which the requests are intercepted and must be having the JWT Token.</p>
     * <p>this method enables checking if the Customer's Token is still Valid in terms of its ActiveTime.</p>
     *
     * @param customerId the Customer ID which their JWT Token should exist.
     */
    public void validateTokenExpiration(Long customerId) {
        tokenRepository.findTokenByCustomerId(customerId).ifPresentOrElse(
                token -> {
                    Instant now = Instant.now();
                    if (token.getActiveTime().isBefore(now)) {
                        tokenRepository.delete(token);
                        throw new ExpiredJwtToken(customerId);
                    }
                    token.setActiveTime(Instant.from(now.plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)));
                    tokenRepository.save(token);
                }, () -> {
                    throw new CustomerTokenNotFound(customerId);
                }
        );
    }

    /**
     * <p>On the Login request, The Customer might have a Token assigned to them already.</p>
     * <p>if the Customer already has a Token it returns the previous Token and its ActiveTime increases.</p>
     * <p>if the Customer does not have a Token yet, means that the Customer whether is New or they logged out from
     * their account, so that a JWT Token is Generated for them.</p>
     *
     * @param customer the customer that attempted to Log in.
     * @return the Jwt token
     */
    public String generateJwtTokenOnLogin(Customer customer) {
        String jwt = jwtUtil.generateJwtToken(customer.getEmail());

        return tokenRepository.findTokenByCustomerId(customer.getId()).map(
                token -> { // The Customer already has a Token.
                    tokenRepository.save(
                            Token.builder()
                                    .customer(customer)
                                    .token(token.getToken())
                                    .activeTime(Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)))
                                    .build()
                    );
                    return token.getToken();
                }).orElseGet(() -> { // The Customer does not have a Token yet.
                    tokenRepository.save(
                            Token.builder()
                                    .customer(customer)
                                    .token(jwt)
                                    .activeTime(Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)))
                                    .build()
                    );
                    return jwt;
                }
        );
    }

    public void deleteTokenOnLogout(long customerId) {
        tokenRepository.findTokenByCustomerId(customerId).ifPresentOrElse(
                tokenRepository::delete,
                () -> {
                    throw new CustomerTokenNotFound(customerId);
                }
        );
    }

    public Integer generateOtpCodeForCustomer(Customer customer) {
        Integer code = new Random().nextInt(100_000, 999_999);
//        Token.builder()
//                .customer(customer)
//                .token(String.valueOf(code))
//                .activeTime()
//                .build();
//        user.setOtpCode(code);
//        user.setOtpCounter(0);
//        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return code;
    }

}
