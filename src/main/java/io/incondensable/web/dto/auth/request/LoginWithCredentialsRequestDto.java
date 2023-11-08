package io.incondensable.web.dto.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginWithCredentialsRequestDto {

    @NotBlank(message = "{email.is.blank}")
    @NotNull(message = "{email.is.blank}")
    @Pattern(message = "{email.invalid}", regexp = "^(?=.{1,64}@)[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "{password.is.null}")
    @NotNull(message = "{password.is.null}")
    private String password;

    @Override
    public String toString() {
        return "{" +
                "email='" + "***" + '\'' +
                ", password='" + "***" + '\'' +
                '}';
    }

}
