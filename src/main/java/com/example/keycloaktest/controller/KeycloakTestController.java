package com.example.keycloaktest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.keycloaktest.dto.IdentityDTO;
import com.example.keycloaktest.service.KeyCloakService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class KeycloakTestController {

	private static Logger log = LoggerFactory.getLogger(KeycloakTestController.class);

	@Autowired
	KeyCloakService service;

	@PostMapping
	public ResponseEntity<IdentityDTO> addUser(@Valid @RequestBody IdentityDTO userDTO) {
		IdentityDTO userIdentity = service.addUser(userDTO);
		return ResponseEntity.ok().body(userIdentity);
	}

}
