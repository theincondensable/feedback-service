package io.incondensable.global.security.service;

import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.User;
import io.incondensable.business.service.UserService;
import io.incondensable.global.security.exceptions.UserEmailDuplicate;
import io.incondensable.global.security.exceptions.UserPasswordMismatch;
import io.incondensable.global.security.vo.FeedbackUserDetails;
import io.incondensable.web.dto.auth.request.LoginWithCredentialsRequestDto;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import io.incondensable.web.dto.auth.response.LoggedInUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abbas
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    /**
     * <p>The Method responsible for logging-in the Users:</p>
     * <p>Validation Starts: First the existence of the input E-Mail Address must be checked.
     * The next step is to compare the input RAW Password with the encoded Password of the User.</p>
     *
     * <p>Now that Validation is passed, SecurityContextHolder must hold the User.</p>
     *
     * <p>Giving JWT Token to the User is the Last Step in Login process.</p>
     *
     * @param req is the incoming request for login purpose with E-Mail and Password of the User.
     * @return the Data showing the UserID, their Token, and the Roles they have.
     */
    public LoggedInUserResponseDto loginWithCredentials(LoginWithCredentialsRequestDto req) {
        User user = userService.getUserByEmail(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new UserPasswordMismatch(user.getEmail());

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        List<String> roles = user.getRoles().stream().map(Role::getAuthority).toList();
        String token = tokenService.generateJwtTokenOnLogin(user)
                .getToken();

        return new LoggedInUserResponseDto(user.getId(), token, roles);
    }

    /**
     * <p>Fetches the Logged-in User using SecurityContextHolder, and by passing the UserID to TokenService,
     * the Main Process of Managing the User who is getting logged-out, gets done.
     * Removing the User who were logged-in from SecurityContextHolder.</p>
     *
     * @return the Email address of the Logged-out User.
     */
    public String logout() {
        FeedbackUserDetails loggedInUser = (FeedbackUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = loggedInUser.getId();
        String email = loggedInUser.getEmail();

        tokenService.deleteTokenOnLogout(userId);

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

        return email;
    }

    /**
     * <p>The requested E-mail Address must be unique, and the Password must be encoded, and delegates the
     * User Creation process to the UserService.</p>
     *
     * @param req Complete information of the User to signup and to let them navigate through the application.
     * @return the persisted User
     */
    public User signup(SignupRequestDto req) {
        if (userService.isEmailDuplicate(req.getUser().getEmail()))
            throw new UserEmailDuplicate(req.getUser().getEmail());

        String encodedPassword = passwordEncoder.encode(req.getUser().getPassword());

        return userService.createUserOnSignup(req, encodedPassword);
    }

}
