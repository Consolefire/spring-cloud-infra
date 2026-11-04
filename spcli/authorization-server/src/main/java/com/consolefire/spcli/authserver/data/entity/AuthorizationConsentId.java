package com.consolefire.spcli.authserver.data.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationConsentId implements Serializable {
    private String registeredClientId;
    private String principalName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationConsentId that = (AuthorizationConsentId) o;
        return Objects.equals(registeredClientId, that.registeredClientId) &&
                Objects.equals(principalName, that.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredClientId, principalName);
    }
}