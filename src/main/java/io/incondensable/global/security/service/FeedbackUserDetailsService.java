package io.incondensable.global.security.service;

import io.incondensable.business.exceptions.user.UserNotFoundWithEmail;
import io.incondensable.business.model.client.User;
import io.incondensable.business.repository.UserRepository;
import io.incondensable.global.security.vo.FeedbackUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author abbas
 */
@Service
public class FeedbackUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public FeedbackUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(
                () -> {
                    throw new UserNotFoundWithEmail(username);
                }
        );

        return FeedbackUserDetails.mapEntityToUserDetails(user);
    }
}
