package io.incondensable.business.service;

import io.incondensable.business.exceptions.customer.CustomerNotFoundException;
import io.incondensable.business.model.auth.Role;
import io.incondensable.business.model.client.Customer;
import io.incondensable.business.repository.CustomerRepository;
import io.incondensable.business.repository.RoleRepository;
import io.incondensable.global.security.vo.RoleEnum;
import io.incondensable.mapper.CustomerMapper;
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
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerService(RoleRepository roleRepository, CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.roleRepository = roleRepository;
        this.customerMapper = customerMapper;
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
        req.getCustomer().getRoles().forEach(
                r -> roles.add(roleRepository.findAllByRole(RoleEnum.valueOf(r)))
        );

        Customer customer = customerMapper.dtoToEntity(req.getCustomer());
        customer.setPassword(encodedPassword);
        customer.setRoles(roles);

        return customerRepository.save(customer);
    }

}
