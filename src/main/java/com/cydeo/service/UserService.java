package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;

import java.util.List;

public interface UserService {

    //controller will call this method meaning UI meaning we work with DTO not entity
    List<UserDTO> listAllUsers();
    //Why username parameter not id because we will use this for example in update and when we click update button we will see username not id. UI will see DTO so also USerDTO does not have id. Then, we will move the information to DB with primary key.
    UserDTO findByUserName(String username);
    void save(UserDTO user);
    void deleteByUserName(String username);
    UserDTO update(UserDTO user);






}
