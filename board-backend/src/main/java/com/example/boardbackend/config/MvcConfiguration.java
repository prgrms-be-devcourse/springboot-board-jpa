package com.example.boardbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // FIXME : 나중에 origin 변경
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.POST.name(),
                        HttpMethod.GET.name(),
                        HttpMethod.DELETE.name()
                );
    }
}
