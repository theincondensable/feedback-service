package io.incondensable.web.dto.auth.request;

import io.incondensable.web.dto.user.request.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {

    @Valid private UserRequestDto user;

    @Override
    public String toString() {
        return "" + user.toString();
    }
}
