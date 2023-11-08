package io.incondensable.global.aspects.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

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

    /**
     * <p>This Pointcut looks for ControllerLog Annotation.</p>
     * <p>You might need some Controllers not to be Logged, so you can omit the Annotation over the Method.</p>
     */
    @Pointcut("@annotation(ControllerLog)")
    public void logPointcut() {
    }

    @Around(value = "logPointcut()")
    public Object around(ProceedingJoinPoint o) throws Throwable {
        long start = new Date().getTime();
        String parameters = o.getArgs().length != 0 ? o.getArgs()[0].toString() : "";
        String scope = extractScope(o.getSignature().getDeclaringTypeName(), o.getSignature().getName());
        logger.logRequest(scope, parameters);

        Object res = o.proceed(); // Here the Controller comes into play.

        long end = new Date().getTime();
        logger.logResponse(scope, res.toString(), (end - start));
        return res;
    }

    private String extractScope(String declaringTypeName, String methodName) {
        String[] split = declaringTypeName.split("\\.");
        String className = split[split.length - 1];
        return className + "." + methodName + "()";
    }

}
