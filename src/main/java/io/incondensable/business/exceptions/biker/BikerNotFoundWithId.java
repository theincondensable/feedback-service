package io.incondensable.business.exceptions.biker;

import io.incondensable.global.exception.BusinessException;
import io.incondensable.global.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class BikerNotFoundWithId extends BusinessException {

    public BikerNotFoundWithId(Long bikerId) {
        super(new ErrorDetails(
                "biker.not.found.with.id",
                HttpStatus.NOT_FOUND,
                bikerId
        ));
    }

}
