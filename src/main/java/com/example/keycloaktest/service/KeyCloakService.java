package com.example.keycloaktest.service;

import java.util.Collections;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.keycloaktest.config.Credentials;
import com.example.keycloaktest.config.KeycloakConfig;
import com.example.keycloaktest.dto.UserDTO;

@Service
public class KeyCloakService {
    private static Logger log = LoggerFactory.getLogger(KeyCloakService.class);

    public void addUser(UserDTO userDTO) {
                CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        instance.create(user);
    }

     public UsersResource getInstance(){
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    }

}
