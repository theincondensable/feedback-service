package io.incondensable.web.dto.customer.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerRequestDto {

    private String firstname;
    private String lastname;

    @NotBlank(message = "{password.is.null}")
    @NotNull(message = "{password.is.null}")
    private String password;

    @NotBlank(message = "{email.is.null}")
    @NotNull(message = "{email.is.null}")
    private String email;
    private String phoneNumber;
    private AddressRequestDto address;
    private Set<String> roles;

    @Override
    public String toString() {
        return "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address.toString() +
                ", roles=" + roles;
    }
}
