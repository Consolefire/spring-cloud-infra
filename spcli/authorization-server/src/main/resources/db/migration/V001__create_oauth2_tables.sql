-- =====================================================================
-- V1: OAuth2 Authorization Server Schema (PostgreSQL Optimized)
-- =====================================================================

CREATE TABLE client
(
    id                            VARCHAR(255)                                       NOT NULL,
    "clientId"                    VARCHAR(255)                                       NOT NULL,
    "clientIdIssuedAt"            TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "clientSecret"                VARCHAR(255)             DEFAULT NULL,
    "clientSecretExpiresAt"       TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "clientName"                  VARCHAR(255)                                       NOT NULL,
    "clientAuthenticationMethods" VARCHAR(1000)                                      NOT NULL,
    "authorizationGrantTypes"     VARCHAR(1000)                                      NOT NULL,
    "redirectUris"                VARCHAR(1000)            DEFAULT NULL,
    "postLogoutRedirectUris"      VARCHAR(1000)            DEFAULT NULL,
    "scopes"                      VARCHAR(1000)                                      NOT NULL,
    "clientSettings"              TEXT                                               NOT NULL,
    "tokenSettings"               TEXT                                               NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE authorization
(
    id                           VARCHAR(255) NOT NULL,
    "registeredClientId"         VARCHAR(255) NOT NULL,
    "principalName"              VARCHAR(255) NOT NULL,
    "authorizationGrantType"     VARCHAR(255) NOT NULL,
    "authorizedScopes"           VARCHAR(1000)            DEFAULT NULL,
    attributes                   TEXT                     DEFAULT NULL,
    state                        VARCHAR(500)             DEFAULT NULL,
    "authorizationCodeValue"     TEXT                     DEFAULT NULL,
    "authorizationCodeIssuedAt"  TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "authorizationCodeExpiresAt" TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "authorizationCodeMetadata"  TEXT                     DEFAULT NULL,
    "accessTokenValue"           TEXT                     DEFAULT NULL,
    "accessTokenIssuedAt"        TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "accessTokenExpiresAt"       TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "accessTokenMetadata"        TEXT                     DEFAULT NULL,
    "accessTokenType"            VARCHAR(255)             DEFAULT NULL,
    "accessTokenScopes"          VARCHAR(1000)            DEFAULT NULL,
    "refreshTokenValue"          TEXT                     DEFAULT NULL,
    "refreshTokenIssuedAt"       TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "refreshTokenExpiresAt"      TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "refreshTokenMetadata"       TEXT                     DEFAULT NULL,
    "oidcIdTokenValue"           TEXT                     DEFAULT NULL,
    "oidcIdTokenIssuedAt"        TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "oidcIdTokenExpiresAt"       TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "oidcIdTokenMetadata"        TEXT                     DEFAULT NULL,
    "oidcIdTokenClaims"          TEXT                     DEFAULT NULL,
    "userCodeValue"              TEXT                     DEFAULT NULL,
    "userCodeIssuedAt"           TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "userCodeExpiresAt"          TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "userCodeMetadata"           TEXT                     DEFAULT NULL,
    "deviceCodeValue"            TEXT                     DEFAULT NULL,
    "deviceCodeIssuedAt"         TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "deviceCodeExpiresAt"        TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    "deviceCodeMetadata"         TEXT                     DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE "authorizationConsent"
(
    "registeredClientId" VARCHAR(255)  NOT NULL,
    "principalName"      VARCHAR(255)  NOT NULL,
    authorities          VARCHAR(1000) NOT NULL,
    PRIMARY KEY ("registeredClientId", "principalName")
);