package com.example.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "SRS API", version = ApiVersion.V1, description = "School Registration System"))
public class OpenApiConfig {



}
