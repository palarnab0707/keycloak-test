package com.example.keycloaktest.config;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Component;

@Component
public class Credentials {
    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
