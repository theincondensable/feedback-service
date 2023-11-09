package io.incondensable.global.security.service;

import io.incondensable.business.model.auth.Token;
import io.incondensable.business.model.client.User;
import io.incondensable.business.repository.TokenRepository;
import io.incondensable.business.service.model.token.TokenDummy;
import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.security.util.JwtUtil;
import io.incondensable.util.FeedbackTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Token Service Unit Tests")
class TokenServiceTest {

    private final JwtUtil jwtUtil = mock(JwtUtil.class);
    private final TokenRepository tokenRepository = mock(TokenRepository.class);

    private final TokenService SUT = new TokenService(jwtUtil, tokenRepository);

    @Test
    @DisplayName("GIVEN User ID WHEN Token by UserID not found THEN an Exception is thrown.")
    public void testTokenNotFoundWithUserId() {
        //GIVEN
        Long userId = 1L;

        when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.empty());

        //WHEN
        BusinessException expectedException = assertThrows(BusinessException.class, () -> SUT.getTokenByUserId(userId));

        //THEN
        assertThat(expectedException.getErrorDetails().getMessage(), is("user.token.not.found"));
    }

    @Test
    @DisplayName("GIVEN User ID WHEN Token by UserID is found THEN the Token is Returned.")
    public void testTokenIsFoundWithUserId() {
        //GIVEN
        Long userId = 1L;
        Token expectedToken = TokenDummy.createActiveToken();

        when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(expectedToken));

        //WHEN
        Token actualToken = SUT.getTokenByUserId(userId);

        //THEN
        assertThat(actualToken.getActiveTime(), is(expectedToken.getActiveTime()));
        assertThat(actualToken.getId(), is(expectedToken.getId()));
    }

    @Test
    @DisplayName("GIVEN User ID WHEN Token by UserID is found BUT it is already Expired THEN Token Entity is DELETED in Database and an Exception is thrown.")
    public void testUserTokenExpired() {
        //GIVEN
        Long userId = 1L;
        Token token = TokenDummy.createExpiredToken();

        when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(token));

        //WHEN
        BusinessException expectedException = assertThrows(BusinessException.class, () -> SUT.validateTokenExpiration(userId));

        //THEN
        verify(tokenRepository, times(1)).delete(any());
        verify(tokenRepository, times(0)).save(any());
        assertThat(expectedException.getErrorDetails().getMessage(), is("jwt.token.is.expired"));
    }

    @Test
    @DisplayName("GIVEN User ID WHEN Token by UserID is found and Active THEN Token's ActiveTime is Updated in Database.")
    public void testUserTokenActiveTimeUpdates() {
        //GIVEN
        Long userId = 1L;
        Token token = TokenDummy.createActiveToken();

        when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(token));

        //WHEN
        SUT.validateTokenExpiration(userId);

        Instant updatedActiveTime = Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES));
        token.setActiveTime(updatedActiveTime);

        //THEN
        verify(tokenRepository, times(0)).delete(any());
        verify(tokenRepository, times(1)).save(any());
        assertThat(token.getActiveTime(), is(updatedActiveTime));
    }

    @Test
    @DisplayName("GIVEN User Entity WHEN User logs in and already had a Token THEN its ActiveTime updates and JWT String remains the same and Token is Returned.")
    public void testUserHadTokenOnLogin() {
        //GIVEN
        User user = FeedbackTestUtil.CUSTOMER_USER;
        Token expectedToken = TokenDummy.createActiveToken();
        String jwt = expectedToken.getToken();
        Instant previousActiveTime  = expectedToken.getActiveTime();

        when(tokenRepository.findTokenByUserId(user.getId())).thenReturn(Optional.of(expectedToken));
        expectedToken.setActiveTime(Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES)));
        when(tokenRepository.save(any())).thenReturn(expectedToken);

        //WHEN
        Token actualToken = SUT.generateJwtTokenOnLogin(user);

        //THEN
        assertThat(jwt, is(actualToken.getToken()));
        assertThat(actualToken.getActiveTime(), not(previousActiveTime));
    }

    @Test
    @DisplayName("GIVEN User Entity WHEN User logs in and did not have any Token THEN new Token is generated and Token is returned.")
    public void testUserNotHadTokenOnLogin() {
        Instant now = Instant.from(Instant.now().plus(JwtUtil.JWT_TOKEN_EXPIRES_IN_MINUTES, ChronoUnit.MINUTES));
        //GIVEN
        User user = FeedbackTestUtil.CUSTOMER_USER;
        Token expectedToken = TokenDummy.createActiveToken();
        String jwt = expectedToken.getToken();
        expectedToken.setToken(null);
        expectedToken.setActiveTime(now);

        when(tokenRepository.findTokenByUserId(user.getId())).thenReturn(Optional.empty());
        when(jwtUtil.generateJwtToken(user.getEmail())).thenReturn(jwt);
        expectedToken.setToken(jwt);
        when(tokenRepository.save(any())).thenReturn(expectedToken);

        //WHEN
        Token actualToken = SUT.generateJwtTokenOnLogin(user);

        //THEN
        assertThat(actualToken.getToken(), is(jwt));
        assertThat(actualToken.getActiveTime(), is(now));
    }

    @Test
    @DisplayName("GIVEN User ID WHEN logout attempt is taken action THEN Token is DELETED.")
    public void testTokenIsDeletedOnLogout() {
        //GIVEN
        long userId = 1L;
        Token token = TokenDummy.createActiveToken();

        when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(token));

        //WHEN
        SUT.deleteTokenOnLogout(userId);

        //THEN
        assertThat(token.getUser(), nullValue());
        verify(tokenRepository, times(1)).delete(any());
    }

}