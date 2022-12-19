package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public List<RoleDTO> listAllRoles() {
        //Controller is calling listAllRoles and requesting all RoleDTOs so, it can show in the dropdown in the UI
        //We need to make a call to DB and get all the roles from table
        //To be able to call the roles from table we are talking about business for the DB
        //Go to repository and find a service which gives us the roles from DB
        //How to call any service here? ==> dependency injection
        //So, I have role entities from DB, need to convert these Role entities to DTOs
        //I used Modelmapper created a class called RoleMapper and there are methods there that will make this convertion
        List<Role> roleList = roleRepository.findAll();

        //takes one entity and converts to DTO but we are trying to return List<RoleDTO>
        //first we can write a for loop for each role
        //second way we can use Stream
        return roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());
       // return roleList; //we need to return RoleDTO this returns Role. We need to convert the entity to DTO

    }

    @Override
    public RoleDTO findById(Long Id) {
        return null;
    }
}
