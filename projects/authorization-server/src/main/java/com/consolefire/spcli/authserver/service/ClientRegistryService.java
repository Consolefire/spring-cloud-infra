package com.consolefire.spcli.authserver.service;

import com.consolefire.spcli.authserver.data.dto.ClientRegistrationRequestDto;
import com.consolefire.spcli.authserver.data.dto.ClientRegistrationResponseDto;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientRegistryService {

    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;
    private final SecureRandom secureRandom = new SecureRandom();
    private static final int SECRET_LENGTH_BYTES = 32;
    private static final String DELETE_CLIENT_SQL = "DELETE FROM oauth2_registered_client WHERE client_id = ?";

    public ClientRegistryService(RegisteredClientRepository registeredClientRepository, PasswordEncoder passwordEncoder,
        JdbcTemplate jdbcTemplate) {
        this.registeredClientRepository = registeredClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    private String generateRandomSecret() {
        byte[] bytes = new byte[SECRET_LENGTH_BYTES];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public Mono<ClientRegistrationResponseDto> registerClient(ClientRegistrationRequestDto request) {
        String plainTextClientSecret = generateRandomSecret();
        String encodedClientSecret = passwordEncoder.encode(plainTextClientSecret);

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(request.getClientId())
            .clientIdIssuedAt(Instant.now())
            .clientSecret(encodedClientSecret)
            .clientAuthenticationMethods(methods ->
                methods.addAll(request.getClientAuthenticationMethods().stream()
                    .map(ClientAuthenticationMethod::new)
                    .collect(Collectors.toSet())))
            .authorizationGrantTypes(grantTypes ->
                grantTypes.addAll(request.getAuthorizationGrantTypes().stream()
                    .map(AuthorizationGrantType::new)
                    .collect(Collectors.toSet())))
            .redirectUris(uris -> uris.addAll(request.getRedirectUris()))
            .scopes(scopes -> scopes.addAll(request.getScopes()))
            // Correct usage of ClientSettings.builder() and requireProofKeyForCodeExchange()
            .clientSettings(ClientSettings.builder()
                .requireProofKey(request.isRequireProofKeyForCodeExchange())
                .build())
            .build();

        return Mono.fromCallable(() -> {
            registeredClientRepository.save(registeredClient);
            return ClientRegistrationResponseDto.builder()
                .id(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientSecret(plainTextClientSecret)
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream()
                    .map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet()))
                .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream()
                    .map(AuthorizationGrantType::getValue).collect(Collectors.toSet()))
                .redirectUris(registeredClient.getRedirectUris())
                .scopes(registeredClient.getScopes())
                .requireProofKeyForCodeExchange(registeredClient.getClientSettings().isRequireProofKey())
                .build();
        });
    }

    public Mono<RegisteredClient> findClientByClientId(String clientId) {
        return Mono.fromCallable(() -> registeredClientRepository.findByClientId(clientId));
    }

    public Flux<RegisteredClient> findAllClients() {
        return Mono.fromCallable(() -> registeredClientRepository.findByClientId("user-service-client"))
            .filter(java.util.Objects::nonNull)
            .flux();
    }

    public Mono<RegisteredClient> updateClient(String clientId, ClientRegistrationRequestDto request) {
        return findClientByClientId(clientId)
            .flatMap(existingClient -> {
                RegisteredClient.Builder updatedClientBuilder = RegisteredClient.from(existingClient)
                    .clientId(request.getClientId());

                updatedClientBuilder.clientSecret(existingClient.getClientSecret());

                updatedClientBuilder
                    .clientAuthenticationMethods(methods -> {
                        methods.clear();
                        methods.addAll(request.getClientAuthenticationMethods().stream()
                            .map(ClientAuthenticationMethod::new)
                            .collect(Collectors.toSet()));
                    })
                    .authorizationGrantTypes(grantTypes -> {
                        grantTypes.clear();
                        grantTypes.addAll(request.getAuthorizationGrantTypes().stream()
                            .map(AuthorizationGrantType::new)
                            .collect(Collectors.toSet()));
                    })
                    .redirectUris(uris -> {
                        uris.clear();
                        uris.addAll(request.getRedirectUris());
                    })
                    .scopes(scopes -> {
                        scopes.clear();
                        scopes.addAll(request.getScopes());
                    })
                    // Correct usage here as well
                    .clientSettings(ClientSettings.builder()
                        .requireProofKey(request.isRequireProofKeyForCodeExchange())
                        .build());

                RegisteredClient updatedClient = updatedClientBuilder.build();
                return Mono.fromCallable(() -> {
                    registeredClientRepository.save(updatedClient);
                    return updatedClient;
                });
            });
    }

    public Mono<Integer> deleteClient(String clientId) {
        return Mono.fromRunnable(() -> {
            int deletedRows = jdbcTemplate.update(DELETE_CLIENT_SQL, clientId);
            if (deletedRows == 0) {
                throw new IllegalStateException("Client not found with ID: " + clientId);
            }
        });
    }
}