package com.kdt.simpleboard.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Board API 명세서",
                description = "게시판 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {
    //    http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/posts/**", "/users/**"};

        return GroupedOpenApi.builder()
                .group("BOARD API v1")
                .pathsToMatch(paths)
                .build();
    }
}
