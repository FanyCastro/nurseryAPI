package com.estefaniacastro.nurseryAPI.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadConfig {

    @Bean
    public Executor taskExecutor() {
        // This will cause any asynchronous execution managed by Spring (such as HTTP requests, @Async, etc.)
        // to use virtual threads instead of traditional ones.
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
