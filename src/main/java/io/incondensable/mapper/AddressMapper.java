package io.incondensable.mapper;

import io.incondensable.business.model.client.Address;
import io.incondensable.web.dto.customer.request.AddressRequestDto;
import io.incondensable.web.dto.customer.response.AddressResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author abbas
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressResponseDto entityToDto(Address address);

    Address dtoToEntity(AddressRequestDto dto);

}
