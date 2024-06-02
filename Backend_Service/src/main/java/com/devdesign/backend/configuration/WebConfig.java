package com.devdesign.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // All endpoints
                .allowedOrigins("*") // Allow all origins (or specify specific ones)
                .allowedMethods("*") // Allow all methods (GET, POST, etc.)
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(false); // If you need to send cookies, etc.
    }
}

