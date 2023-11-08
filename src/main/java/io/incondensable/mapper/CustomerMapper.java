package io.incondensable.mapper;

import io.incondensable.business.model.client.Customer;
import io.incondensable.web.dto.customer.request.CustomerRequestDto;
import io.incondensable.web.dto.customer.response.CustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author abbas
 */
@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponseDto entityToDto(Customer customer);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    Customer dtoToEntity(CustomerRequestDto dto);
}
