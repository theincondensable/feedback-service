package io.incondensable.web.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author abbas
 */
@Getter
@Setter
@AllArgsConstructor
public class LoggedInCustomerResponseDto {
    private Long customerId;
    private String token;
    private List<String> roles;
}
