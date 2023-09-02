package com.example.keycloaktest.service;

import java.util.Collections;
import java.util.List;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.keycloaktest.config.Credentials;
import com.example.keycloaktest.config.KeycloakConfig;
import com.example.keycloaktest.dto.IdentityDTO;
import com.example.keycloaktest.exception.BadRequestException;
import com.example.keycloaktest.mapper.UserIdentityMapper;
import com.example.keycloaktest.model.User;
import com.example.keycloaktest.repository.UserRepository;

import jakarta.ws.rs.core.Response;

@Service
public class KeyCloakService {
    private static Logger log = LoggerFactory.getLogger(KeyCloakService.class);

    @Autowired
    private KeycloakConfig keycloakConfig;

    @Autowired
    private UserIdentityMapper userIdentityMapper;

    @Autowired
    private UserRepository userRepository;

    public IdentityDTO addUser(IdentityDTO userDTO) {
        log.debug("Storing User details to SSO {}", userDTO);
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getLoginId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        Response resp = instance.create(user);
        // resp.
        if (resp.getStatus() != 201) {
            log.error("Couldn't create user in Keycloack SSO server!!! {}",  resp);
            throw new BadRequestException("Couldn't create user in Keycloack SSO server!!!");
        }

        String identityId = null;
        List<UserRepresentation> userList = getUser(userDTO.getLoginId());
        if (userList.isEmpty()) {
            throw new BadRequestException("UserLogin not available at SSO");
        } else {
            UserRepresentation ur = userList.get(0);
            identityId = ur.getId();
            userDTO.setIdentityId(identityId);
            User identityUser =  userIdentityMapper.toEntity(userDTO);
            userRepository.save(identityUser);
        }
        return userDTO;
    }

    private UsersResource getInstance() {
        return keycloakConfig.getInstance().realm(keycloakConfig.getRelamName()).users();
    }

    private List<UserRepresentation> getUser(String userName) {
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;
    }

}
