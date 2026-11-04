package com.consolefire.spcli.authserver.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authorization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authorization {

    @Id
    @Column(nullable = false, length = 255)
    private String id;

    @Column(name = "registeredClientId", nullable = false, length = 255)
    private String registeredClientId;

    @Column(name = "principalName", nullable = false, length = 255)
    private String principalName;

    @Column(name = "authorizationGrantType", nullable = false, length = 255)
    private String authorizationGrantType;

    @Column(name = "authorizedScopes", length = 1000)
    private String authorizedScopes;

    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;

    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorizationCodeValue", columnDefinition = "TEXT")
    private String authorizationCodeValue;

    @Column(name = "authorizationCodeIssuedAt")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorizationCodeExpiresAt")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "authorizationCodeMetadata", columnDefinition = "TEXT")
    private String authorizationCodeMetadata;

    @Column(name = "accessTokenValue", columnDefinition = "TEXT")
    private String accessTokenValue;

    @Column(name = "accessTokenIssuedAt")
    private Instant accessTokenIssuedAt;

    @Column(name = "accessTokenExpiresAt")
    private Instant accessTokenExpiresAt;

    @Column(name = "accessTokenMetadata", columnDefinition = "TEXT")
    private String accessTokenMetadata;

    @Column(name = "accessTokenType", length = 255)
    private String accessTokenType;

    @Column(name = "accessTokenScopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "refreshTokenValue", columnDefinition = "TEXT")
    private String refreshTokenValue;

    @Column(name = "refreshTokenIssuedAt")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refreshTokenExpiresAt")
    private Instant refreshTokenExpiresAt;

    @Column(name = "refreshTokenMetadata", columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    @Column(name = "oidcIdTokenValue", columnDefinition = "TEXT")
    private String oidcIdTokenValue;

    @Column(name = "oidcIdTokenIssuedAt")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidcIdTokenExpiresAt")
    private Instant oidcIdTokenExpiresAt;

    @Column(name = "oidcIdTokenMetadata", columnDefinition = "TEXT")
    private String oidcIdTokenMetadata;

    @Column(name = "oidcIdTokenClaims", columnDefinition = "TEXT")
    private String oidcIdTokenClaims;

    @Column(name = "userCodeValue", columnDefinition = "TEXT")
    private String userCodeValue;

    @Column(name = "userCodeIssuedAt")
    private Instant userCodeIssuedAt;

    @Column(name = "userCodeExpiresAt")
    private Instant userCodeExpiresAt;

    @Column(name = "userCodeMetadata", columnDefinition = "TEXT")
    private String userCodeMetadata;

    @Column(name = "deviceCodeValue", columnDefinition = "TEXT")
    private String deviceCodeValue;

    @Column(name = "deviceCodeIssuedAt")
    private Instant deviceCodeIssuedAt;

    @Column(name = "deviceCodeExpiresAt")
    private Instant deviceCodeExpiresAt;

    @Column(name = "deviceCodeMetadata", columnDefinition = "TEXT")
    private String deviceCodeMetadata;
}
