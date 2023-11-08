package io.incondensable.global.security.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author abbas
 */
@Component
public class JwtUtil {

    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    public static final int JWT_TOKEN_EXPIRES_IN_MINUTES = 60;

    private static final String BEARER_PREFIX = "Bearer ";

    public String generateJwtToken(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Optional<String> extractJwtToken(HttpServletRequest request) {
        try {
            String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(auth)) {
                if (auth.startsWith(BEARER_PREFIX)) {
                    String token = auth.substring(BEARER_PREFIX.length()).trim();
                    if (!token.isBlank() && validateTokens(token))
                        return Optional.of(token);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public boolean validateTokens(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
