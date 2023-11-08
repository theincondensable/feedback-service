package io.incondensable.web.controller;

import io.incondensable.business.service.CustomerService;
import io.incondensable.global.aspects.log.aspect.ControllerLog;
import io.incondensable.mapper.CustomerMapper;
import io.incondensable.web.dto.customer.response.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    public CustomerController(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    @ControllerLog
    @GetMapping("/{email}")
    public ResponseEntity<CustomerResponseDto> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(
                customerMapper.entityToDto(
                        customerService.getCustomerByEmail(email)
                )
        );
    }

}
