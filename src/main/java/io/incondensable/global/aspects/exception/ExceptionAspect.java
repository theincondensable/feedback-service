package io.incondensable.global.aspects.exception;

import io.incondensable.global.exception.BusinessException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @author abbas
 */
@Aspect
@Configuration
public class ExceptionAspect {

    private final ExceptionLogger logger;

    public ExceptionAspect(ExceptionLogger logger) {
        this.logger = logger;
    }

    @Pointcut("execution(* io.incondensable..service..*.*(..))")
    public void exceptionPointcut() {
    }

    @AfterThrowing(value = "exceptionPointcut()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        if (ex instanceof BusinessException) {
            logger.logBusinessException((BusinessException) ex);
            throw new BusinessException((BusinessException) ex);
        } else {
            try {
                throw ex;
            } catch (Exception e) {
                logger.logJavaException(e);
            }
        }
    }

}
