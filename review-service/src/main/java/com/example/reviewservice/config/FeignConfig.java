package com.example.reviewservice.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    feign.Logger.Level feifnLogLevel() {
        return Logger.Level.FULL;
    }
}
