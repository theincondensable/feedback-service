package io.incondensable.mapper;

import io.incondensable.business.model.client.User;
import io.incondensable.web.dto.user.request.UserRequestDto;
import io.incondensable.web.dto.user.response.UserResponseDto;
import lombok.RequiredArgsConstructor;

/**
 * @author abbas
 */
@Mapper
@RequiredArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public UserResponseDto entityToDto(User user) {
        UserResponseDto dto = new UserResponseDto();

        dto.setAddress(addressMapper.entityToDto(user.getAddress()));
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());

        return dto;
    }

    public User dtoToEntity(UserRequestDto dto) {
        return User.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .address(addressMapper.dtoToEntity(dto.getAddress()))
                .build();
    }
}
