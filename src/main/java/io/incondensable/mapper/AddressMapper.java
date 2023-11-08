package io.incondensable.mapper;

import io.incondensable.business.model.client.Address;
import io.incondensable.web.dto.customer.request.AddressRequestDto;
import io.incondensable.web.dto.customer.response.AddressResponseDto;
import org.springframework.stereotype.Component;

/**
 * @author abbas
 */
@Component
public class AddressMapper {

    public AddressResponseDto entityToDto(Address address) {
        AddressResponseDto dto = new AddressResponseDto();

        dto.setCountry(address.getCountry());
        dto.setCity(address.getCity());
        dto.setStreet(address.getStreet());
        dto.setZipcode(address.getZipcode());

        return dto;
    }

    public Address dtoToEntity(AddressRequestDto dto) {
        return Address.builder()
                .country(dto.getCountry())
                .city(dto.getCity())
                .street(dto.getStreet())
                .zipcode(dto.getZipcode())
                .build();
    }

}
