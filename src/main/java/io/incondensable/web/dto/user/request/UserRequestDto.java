package io.incondensable.web.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String firstname;
    private String lastname;

    @NotBlank(message = "{password.is.null}")
    @NotNull(message = "{password.is.null}")
    private String password;

    @NotBlank(message = "{email.is.null}")
    @NotNull(message = "{email.is.null}")
    @Pattern(message = "{email.is.invalid}", regexp = "^(?=.{1,64}@)[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "{phone.number.is.null}")
    @NotNull(message = "{phone.number.is.null}")
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
