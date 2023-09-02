package com.example.keycloaktest.mapper;

import org.mapstruct.Mapper;

import com.example.keycloaktest.dto.IdentityDTO;
import com.example.keycloaktest.model.User;

/**
 * Mapper for the entity {@link User} and its DTO {@link IdentityDTO}
 * 
 * @author Shouvick Paul
 */

@Mapper(componentModel = "spring", uses = {})
public interface UserIdentityMapper extends EntityMapper<IdentityDTO, User> {

}
