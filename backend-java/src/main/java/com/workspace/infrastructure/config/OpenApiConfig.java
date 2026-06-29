package com.workspace.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    private static final String TITLE = "Smart Workspace API";
    private static final String AUTH = "Bearer Authentication";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                    .title(TITLE)
                    .version("1.0.0")
                    .description("API documentation for Smart Workspace platform built with Spring Boot"))
            .addSecurityItem(new SecurityRequirement().addList(AUTH))
            .components(new Components()
                            .addSecuritySchemes(AUTH, new SecurityScheme()
                            .name(AUTH)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
    }
}
