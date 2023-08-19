package com.example.keycloaktest.controller;

import java.util.*;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.core.Response;

@RestController
public class KeycloakTestController {

	private static Logger log = LoggerFactory.getLogger(KeycloakTestController.class);

	@Autowired
	Environment env;

	@PostMapping("/save")
	public ResponseEntity<IdentityDTO> saveUser(@RequestBody IdentityDTO identityDto) throws Exception {
		var responseDto = new IdentityDTO();
		String serverUrl = env.getProperty("sso.server-url");
		String realm = env.getProperty("sso.realm");
		String ssoUserName = env.getProperty("sso.username");
		String ssoPassword = env.getProperty("sso.password");
		String clientId = env.getProperty("sso.client-id");
		String authRealm = env.getProperty("sso.auth-realm");
		ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(createAllTrustingClient());
		var keycloak = KeycloakBuilder.builder().serverUrl(serverUrl).realm(realm).username(ssoUserName)
				.password(ssoPassword).clientId(clientId)
				.resteasyClient(new ResteasyClientBuilderImpl().httpEngine(engine).connectionPoolSize(10).build()).build();

		var credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(identityDto.getPassword());
		credential.setTemporary(identityDto.isTemporaryCredentials());
		
//		if("S".equalsIgnoreCase(identityDto.getUserType())) {
//			String loginName = seriesGenerator.getLoginSeries("SUPPLIER");
//			identityDto.setLoginId(loginName);
//		}

		var user = new UserRepresentation();
		user.setUsername(identityDto.getLoginId());
		user.setFirstName(identityDto.getFirstName());
		user.setLastName(identityDto.getLastName());
		user.setEmail(identityDto.getEmailId());
		user.setCredentials(List.of(credential));
		user.setEnabled(true);
		Map<String, List<String>> attributes = new HashMap<>();
		attributes.put("userType", Arrays.asList(identityDto.getUserType()));
		user.setAttributes(attributes);

		Response result = keycloak.realm(authRealm).users().create(user);

		responseDto.setMessage("Created");
		if (result.getStatus() != 201) {
			log.error("Couldn't create user in RH SSO server.");
			responseDto.setMessage("Couldn't create user in RH SSO server.");

		}

		String identityId = null;
		var usersResource = keycloak.realm(authRealm).users();
		List<UserRepresentation> userList = usersResource.search(identityDto.getLoginId());
		if (userList.isEmpty()) {
			throw new Exception("User Login is not available at SSO");
		} else {
			UserRepresentation ur = userList.get(0);
			identityId = ur.getId();

//			var identityUser = new User();
//			identityUser.setIdentityId(identityId);
//			identityUser.setLoginName(identityDto.getLoginId());
//			identityUser.setType(identityDto.getUserType());
//			userRepository.save(identityUser);

			log.info(identityId, " Created.... verify in RH SSO!");
			responseDto.setIdentityId(identityId);
			//responseDto.setStatus(result.getStatus());
			responseDto.setLoginId(identityDto.getLoginId());
			//assigning default post to contractor
			//assignPost(identityUser);
		}

		result.close();

		return ResponseEntity.ok().body(responseDto);
		
	}

	private DefaultHttpClient createAllTrustingClient() throws GeneralSecurityException
	{
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));



		TrustStrategy trustStrategy = new TrustStrategy() {
		public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		return true;
		}
		};
		SSLSocketFactory factory = new SSLSocketFactory(trustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		registry.register(new Scheme("https", 443, factory));



		ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
		mgr.setMaxTotal(1000);
		mgr.setDefaultMaxPerRoute(1000);



		DefaultHttpClient client = new DefaultHttpClient(mgr, new DefaultHttpClient().getParams());
		return client;
		}

}
