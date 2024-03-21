package com.wtacademyplatform.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // You can specify specific origins instead of allowing all
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Include DELETE method
                        .allowedHeaders("*"); // You can specify specific headers instead of allowing all
            }
        };
    }
}