package io.incondensable.global.aspects.exception;

import io.incondensable.global.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author abbas
 */
@Component
public class ExceptionLogger {

    private final Logger logger = LogManager.getLogger(ExceptionLogger.class);

    public void logBusinessException(BusinessException exception) {
        logger.error("Http Status: " + exception.getErrorDetails().getHttpStatus());
    }

    public void logJavaException(Throwable t) {
        logger.error(t.getStackTrace());
    }

}
