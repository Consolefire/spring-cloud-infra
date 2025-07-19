package com.consolefire.spcli.authserver.config;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class AuthServerUserDetailConfiguration {

    @Value("${gateway.services.user-service.name}")
    private String userServiceGatewayId;

    @Value("${gateway.services.user-service.path-prefix}")
    private String userServiceGatewayPathPrefix;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Mark WebClient.Builder as @LoadBalanced so it can resolve service IDs via Eureka
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(PasswordEncoder passwordEncoder,
        WebClient.Builder loadBalancedWebClientBuilder) {
        // Build the WebClient from the @LoadBalanced builder
        WebClient webClient = loadBalancedWebClientBuilder
            .baseUrl(String.format("http://%s", userServiceGatewayId)) // Use the API Gateway's service ID from YAML
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        return username -> {
            // Call the API Gateway endpoint that routes to user-service
            // The WebClient will resolve "http://xpenser-user-service" to an actual instance of your gateway
            return webClient.get()
                .uri(userServiceGatewayPathPrefix + "/details/{username}", username) // Use the path prefix from YAML
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                    clientResponse -> Mono.error(new RuntimeException(
                        "Error fetching user details from API Gateway/user-service: " + clientResponse.statusCode()
                            + " for user: " + username))
                )
                .bodyToMono(Map.class)
                .map(userMap -> {
                    String fetchedUsername = (String) userMap.get("username");
                    String fetchedPassword = (String) userMap.get("password");
                    String rolesString = (String) userMap.get("roles");

                    if (fetchedUsername == null || fetchedPassword == null || rolesString == null) {
                        throw new IllegalArgumentException(
                            "Invalid user details received from user-service via gateway for username: " + username);
                    }

                    return User.withUsername(fetchedUsername)
                        .password(fetchedPassword)
                        .roles(rolesString.split(","))
                        .build();
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found via gateway: " + username)));
        };
    }

}
