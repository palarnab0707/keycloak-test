package com.example.keycloaktest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.keycloaktest.dto.UserDTO;
import com.example.keycloaktest.service.KeyCloakService;

@RestController
@RequestMapping(path = "/api/user")
public class KeycloakTestController {

	private static Logger log = LoggerFactory.getLogger(KeycloakTestController.class);

	@Autowired
	KeyCloakService service;

	@PostMapping
	public String addUser(@RequestBody UserDTO userDTO) {
		service.addUser(userDTO);
		return "User Added Successfully.";
	}

}
