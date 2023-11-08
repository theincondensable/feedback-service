package io.incondensable.web.dto.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginWithOtpRequestDto {

    @NotBlank(message = "{email.is.blank}")
    @NotNull(message = "{email.is.blank}")
    @Pattern(message = "{email.invalid}", regexp = "^(?=.{1,64}@)[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull(message = "{opt.code.not.entered}")
    @Range(min = 100000, max = 999999, message = "{otp.code.length}")
    private Integer otpCode;

}
