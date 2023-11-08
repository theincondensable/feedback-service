package io.incondensable.mapper;

import io.incondensable.business.model.client.Customer;
import io.incondensable.web.dto.customer.request.CustomerRequestDto;
import io.incondensable.web.dto.customer.response.CustomerResponseDto;
import lombok.RequiredArgsConstructor;

/**
 * @author abbas
 */
@Mapper
@RequiredArgsConstructor
public class CustomerMapper {

    private final AddressMapper addressMapper;

    public CustomerResponseDto entityToDto(Customer customer) {
        CustomerResponseDto dto = new CustomerResponseDto();

        dto.setAddress(addressMapper.entityToDto(customer.getAddress()));
        dto.setFirstname(customer.getFirstname());
        dto.setLastname(customer.getLastname());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());

        return dto;
    }

    public Customer dtoToEntity(CustomerRequestDto dto) {
        return Customer.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .address(addressMapper.dtoToEntity(dto.getAddress()))
                .build();
    }
}
