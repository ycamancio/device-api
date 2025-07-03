package com.sandbox.device.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Device API")
                        .version("1.0")
                        .description("Device API with CRUD capabilities")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com")));
    }
}
