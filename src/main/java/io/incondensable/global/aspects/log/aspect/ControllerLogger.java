package io.incondensable.global.aspects.log.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author abbas
 */
@Component
public class ControllerLogger {

    private final Logger logger = LogManager.getLogger(ControllerLogger.class);

    public void logRequest(String parameters, String scope) {
        logger.info(
                "Request in: " + scope
                        + (parameters.isBlank() ? "" : " with Parameters: " + parameters)
        );
    }

    public void logResponse(String parameters, String scope) {
        logger.info("Response in: " + scope
                + (parameters.isBlank() ? "" : " with Parameters: " + parameters)
        );
    }

}
