package com.consolefire.spcli.authserver.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authorizationConsent")
@IdClass(AuthorizationConsentId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationConsent {

    @Id
    @Column(name = "registeredClientId", nullable = false, length = 255)
    private String registeredClientId;

    @Id
    @Column(name = "principalName", nullable = false, length = 255)
    private String principalName;

    @Column(name = "authorities", nullable = false, length = 1000)
    private String authorities;
}