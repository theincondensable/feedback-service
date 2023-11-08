package io.incondensable.web.dto.customer.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomerResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private AddressResponseDto address;

    @Override
    public String toString() {
        return "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address.toString();
    }
}
