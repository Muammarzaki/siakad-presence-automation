package com.github.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {

    @Bean
    @Primary
    Clock defaultClock() {
        return Clock.systemDefaultZone();
    }
}
