package com.consolefire.spcli.user.controller;

import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @GetMapping("/users/me")
    public Mono<String> getCurrentUser(Mono<Principal> principal) {
        return principal
            .map(p -> "Hello, " + p.getName() + "! This is a protected resource.")
            .defaultIfEmpty("Hello, anonymous user!");
    }

    @GetMapping("/users/jwt-details")
    public Mono<String> getJwtDetails(@AuthenticationPrincipal Jwt jwt) {
        return Mono.just("JWT claims: " + jwt.getClaims());
    }

    @GetMapping("/public")
    public Mono<String> publicEndpoint() {
        return Mono.just("This is a public endpoint.");
    }
}