package io.incondensable.web.dto.auth.request;

import io.incondensable.web.dto.customer.request.CustomerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {

    private CustomerRequestDto customer;

    @Override
    public String toString() {
        return "" + customer.toString();
    }
}
