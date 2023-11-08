package io.incondensable.web.dto.customer.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String password;
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
