package io.incondensable.global.security.service;

import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.Customer;
import io.incondensable.business.service.CustomerService;
import io.incondensable.global.security.exceptions.CustomerPasswordMismatch;
import io.incondensable.global.security.exceptions.CustomerEmailDuplicate;
import io.incondensable.global.security.util.JwtUtil;
import io.incondensable.global.security.vo.CustomerDetails;
import io.incondensable.web.dto.auth.request.LoginWithCredentialsRequestDto;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import io.incondensable.web.dto.auth.response.LoggedInCustomerResponseDto;
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
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    /**
     * @param req is the incoming request for login purpose with E-Mail and Password of the Customer.
     * @return the Data showing the CustomerID, their Token, and the Roles they have.
     */
    public LoggedInCustomerResponseDto loginWithCredentials(LoginWithCredentialsRequestDto req) {
        Customer customer = customerService.getCustomerByEmail(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), customer.getPassword()))
            throw new CustomerPasswordMismatch(customer.getEmail());

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customer.getEmail(),
                        customer.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        List<String> roles = customer.getRoles().stream().map(Role::getAuthority).toList();
        String token = tokenService.generateJwtTokenOnLogin(customer);

        return new LoggedInCustomerResponseDto(customer.getId(), token, roles);
    }

    public String logout() {
        CustomerDetails loggedInCustomer = (CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long customerId = loggedInCustomer.getId();
        String email = loggedInCustomer.getEmail();

        tokenService.deleteTokenOnLogout(customerId);

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

        return email;
    }

    /**
     * <p>Only for the sake of simplicity, this method creates a Complete Customer in place.</p>
     * <p>The requested E-mail Address must be unique, and the Password must be encoded.</p>
     *
     * @param req Complete information of the Customer to signup and to navigate through the application.
     * @return the persisted Customer
     */
    public Customer signup(SignupRequestDto req) {
        if (customerService.isEmailDuplicate(req.getCustomer().getEmail()))
            throw new CustomerEmailDuplicate(req.getCustomer().getEmail());

        String encodedPassword = passwordEncoder.encode(req.getCustomer().getPassword());

        return customerService.createCustomerOnSignup(req, encodedPassword);
    }

//    public LoggedInUserResponseDto loginWithOtp(LoginWithOtpRequestDto req) {
//        Customer customer = customerService.getCustomerByEmail(req.getEmail());
//
//        if ()
//    }

}
