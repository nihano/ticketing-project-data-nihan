package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
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
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
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
//        return roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());
       // return roleList; //we need to return RoleDTO this returns Role. We need to convert the entity to DTO

        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDTO())).collect(Collectors.toList());

//        return roleList.stream().map(role -> mapperUtil.convert(role,RoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long Id) {
        return roleMapper.convertToDto(roleRepository.findById(Id).get());
        //get() because it is optional
        //was returning Role we converted to RoleDTO
    }
}
