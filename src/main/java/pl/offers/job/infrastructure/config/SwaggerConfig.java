package pl.offers.job.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "JWT Token";
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer")
                                .in(SecurityScheme.In.HEADER).name("Authorization")))
                .info(new Info().title("Job Offers API").description("Job Offers API description").version("1.0.0"))
                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName, Arrays.asList("read", "write")));
    }

}
