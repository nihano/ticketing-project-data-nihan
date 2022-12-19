package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleService {
    //We are not extending from JPARepository here because this is Service DTO this will be transferred to UI
    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long Id);




}
