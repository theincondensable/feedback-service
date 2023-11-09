package io.incondensable.business.service.model.user;

import io.incondensable.business.model.client.User;
import io.incondensable.web.dto.auth.request.SignupRequestDto;
import io.incondensable.web.dto.user.request.AddressRequestDto;
import io.incondensable.web.dto.user.request.UserRequestDto;

import java.util.stream.Collectors;

/**
 * @author abbas
 */
public class SignupRequestDtoDummy {
    private SignupRequestDtoDummy() {

    }

    public static SignupRequestDto create(User expected) {
        SignupRequestDto request = new SignupRequestDto();
        request.setUser(new UserRequestDto(
                expected.getFirstname(),
                expected.getLastname(),
                UserDummy.decodedPassword(),
                expected.getEmail(),
                expected.getPhoneNumber(),
                new AddressRequestDto(),
                expected.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toSet())
        ));
        return request;
    }
}
