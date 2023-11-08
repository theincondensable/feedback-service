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

    /**
     * <p>Logs the Incoming Requests.</p>
     * @param scope is the Controller's Class Name joined with Controller's Method Name.
     * @param parameters the incoming Parameters.
     */
    public void logRequest(String scope, String parameters) {
        logger.info(
                "Request in: " + scope
                        + (parameters.isBlank() ? "" : " with Parameters: " + parameters)
        );
    }

    /**
     * <p>Logs the Outgoing Responses.</p>
     * @param scope is the Controller's Class Name joined with Controller's Method Name.
     * @param parameters the outgoing Parameters.
     * @param duration the duration of the request to response.
     */
    public void logResponse(String scope, String parameters, long duration) {
        logger.info("Response in: " + scope
                + (parameters.isBlank() ? "" : " with Parameters: " + parameters)
                + " in " + duration + " milliseconds."
        );
    }

}
