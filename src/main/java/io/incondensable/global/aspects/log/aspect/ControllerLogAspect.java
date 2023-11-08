package io.incondensable.global.aspects.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @author abbas
 */
@Aspect
@Configuration
public class ControllerLogAspect {

    private final ControllerLogger logger;

    public ControllerLogAspect(ControllerLogger logger) {
        this.logger = logger;
    }

    @Pointcut("@annotation(io.incondensable.global.aspects.log.aspect.ControllerLog)")
    public void logPointcut() {
    }

    @Around(value = "logPointcut()")
    public Object around(ProceedingJoinPoint o) throws Throwable {
        String parameters = o.getArgs()[0].toString();
        String scope = extractScope(o.getSignature().getDeclaringTypeName(), o.getSignature().getName());

        logger.logRequest(parameters, scope);

        Object res = o.proceed();

        logger.logResponse(res.toString(), scope);
        return res;
    }

    private String extractScope(String declaringTypeName, String methodName) {
        String[] split = declaringTypeName.split("\\.");
        String className = split[split.length - 1];
        return className + "." + methodName + "()";
    }

}
