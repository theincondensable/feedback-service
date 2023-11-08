package io.incondensable.business.service;

import io.incondensable.business.exceptions.customer.CustomerNotFoundException;
import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.Address;
import io.incondensable.business.model.client.Customer;
import io.incondensable.business.repository.CustomerRepository;
import io.incondensable.business.repository.RoleRepository;
import io.incondensable.global.security.vo.RoleEnum;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author abbas
 */
@Service
public class CustomerService {

    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(RoleRepository roleRepository, CustomerRepository customerRepository) {
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> {
                    throw new CustomerNotFoundException(email);
                });
    }

    public boolean isEmailDuplicate(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    public Customer createCustomerOnSignup(SignupRequestDto req, String encodedPassword) {
        Set<Role> roles = new HashSet<>();
        req.getCustomer().getRoles().forEach(r -> roles.add(roleRepository.findAllByRole(RoleEnum.valueOf(r))));

        Customer customer = Customer.builder()
                .firstname(req.getCustomer().getFirstname())
                .lastname(req.getCustomer().getLastname())
                .email(req.getCustomer().getEmail())
                .password(encodedPassword)
                .address(
                        Address.builder()
                                .country(req.getCustomer().getAddress().getCountry())
                                .city(req.getCustomer().getAddress().getCity())
                                .street(req.getCustomer().getAddress().getStreet())
                                .zipcode(req.getCustomer().getAddress().getZipcode())
                                .build())
                .phoneNumber(req.getCustomer().getPhoneNumber())
                .roles(roles)
                .build();

        return customerRepository.save(customer);
    }

}
