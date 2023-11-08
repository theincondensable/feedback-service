package io.incondensable.global.security.exceptions;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class ExpiredJwtToken extends BusinessException {

    public ExpiredJwtToken(Long id) {
        super(new ErrorDetails(
                "jwt.token.is.expired",
                HttpStatus.FORBIDDEN,
                String.valueOf(id)
        ));
    }

}
