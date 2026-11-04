package com.consolefire.spcli.authserver.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

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

    @Bean
    @Profile("!with-eureka-discovery")
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public UserDetailsService userDetailsService(RestClient.Builder loadBalancedRestClientBuilder) {
        RestClient restClient = loadBalancedRestClientBuilder
                .baseUrl(String.format("http://%s", userServiceGatewayId))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return username -> {
            try {
                Map<String, Object> userMap = restClient.get()
                        .uri(userServiceGatewayPathPrefix + "/details/{username}", username)
                        .retrieve()
                        .body(Map.class);

                if (userMap == null) {
                    throw new UsernameNotFoundException("User not found via gateway: " + username);
                }

                String fetchedUsername = (String) userMap.get("username");
                String fetchedPassword = (String) userMap.get("password");
                String rolesString = (String) userMap.get("roles");

                if (fetchedUsername == null || fetchedPassword == null || rolesString == null) {
                    throw new UsernameNotFoundException("Invalid user details received for username: " + username);
                }

                return User.withUsername(fetchedUsername)
                        .password(fetchedPassword)
                        .roles(rolesString.split(","))
                        .build();
            } catch (Exception e) {
                throw new UsernameNotFoundException("Error fetching user details from gateway for user: " + username, e);
            }
        };
    }
}