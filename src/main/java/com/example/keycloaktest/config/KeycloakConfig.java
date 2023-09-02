package com.example.keycloaktest.config;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakConfig  {
    static Keycloak keycloak = null;
    @Value("${sso.server-url}")
    private String serverUrl = "http://localhost:8080";
    @Value("${sso.realm}")
    private String realm = "master";
    @Value("${sso.client-id}")
    private String clientId = "admin-cli";
    @Value("${sso.client-secret}")
    private String clientSecret = "66tpSSB0LMO8OdEVRKSMvlWdRZq9VZW2";
    @Value("${sso.username}")
    private String userName = "admin";
    @Value("${sso.password}")
    private String password = "admin";

    public KeycloakConfig() {
    }

    public String getRelamName(){
        return realm;
    }

    public Keycloak getInstance(){
        if(keycloak == null){
           
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilderImpl()
                                   .connectionPoolSize(10)
                                   .build()
                                   )
                    .build();
        }
        return keycloak;
    }
}
