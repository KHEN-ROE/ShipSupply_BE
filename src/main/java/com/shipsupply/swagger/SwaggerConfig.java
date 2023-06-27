package com.shipsupply.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Title")
                        .description("API Description")
                        .version("API version")
                        .termsOfService("Terms of service URL")
                        .contact(new Contact().name("Name").url("URL").email("email")));
    }
}
