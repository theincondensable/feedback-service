package io.incondensable.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @author abbas
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task APIs",
                version = "0.0.1",
                description = "The APIs Documentation."
        )
)
public class OpenApiConfiguration {
}
