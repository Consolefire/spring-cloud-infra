package com.consolefire.spcli.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class UserServiceSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/actuator/**").permitAll() // Allow actuator endpoints for discovery/config client
                .anyExchange().authenticated() // All other requests require authentication
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Configure as OAuth2 Resource Server using JWTs
        return http.build();
    }

}
