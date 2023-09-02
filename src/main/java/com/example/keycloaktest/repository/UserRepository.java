package com.example.keycloaktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.keycloaktest.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
