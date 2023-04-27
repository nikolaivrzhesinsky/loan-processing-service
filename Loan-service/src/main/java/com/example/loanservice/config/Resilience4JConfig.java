package com.example.loanservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4JConfig {

//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory>globalCustomConfiguration(){
//
//        CircuitBreakerConfig circuitBreakerConfig= CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .waitDurationInOpenState(Duration.ofMillis(1000))
//                .slidingWindowSize(5)
//                .slowCallDurationThreshold(Duration.ofSeconds(1))
//                .build();
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(circuitBreakerConfig)
//                .build());
//
//    }
}
