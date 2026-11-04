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
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @Column(nullable = false, length = 255)
    private String id;

    @Column(name = "clientId", nullable = false, length = 255)
    private String clientId;

    @Column(name = "clientIdIssuedAt", nullable = false)
    private Instant clientIdIssuedAt;

    @Column(name = "clientSecret", length = 255)
    private String clientSecret;

    @Column(name = "clientSecretExpiresAt")
    private Instant clientSecretExpiresAt;

    @Column(name = "clientName", nullable = false, length = 255)
    private String clientName;

    @Column(name = "clientAuthenticationMethods", nullable = false, length = 1000)
    private String clientAuthenticationMethods;

    @Column(name = "authorizationGrantTypes", nullable = false, length = 1000)
    private String authorizationGrantTypes;

    @Column(name = "redirectUris", length = 1000)
    private String redirectUris;

    @Column(name = "postLogoutRedirectUris", length = 1000)
    private String postLogoutRedirectUris;

    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    @Column(name = "clientSettings", nullable = false, columnDefinition = "TEXT")
    private String clientSettings;

    @Column(name = "tokenSettings", nullable = false, columnDefinition = "TEXT")
    private String tokenSettings;
}
