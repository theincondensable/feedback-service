package io.incondensable.global.security.service;

import io.incondensable.business.exceptions.customer.CustomerNotFoundWithEmail;
import io.incondensable.business.model.client.Customer;
import io.incondensable.business.repository.CustomerRepository;
import io.incondensable.global.security.vo.CustomerDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author abbas
 */
@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(username).orElseThrow(
                () -> {
                    throw new CustomerNotFoundWithEmail(username);
                }
        );

        return CustomerDetails.mapEntityToUserDetails(customer);
    }
}
