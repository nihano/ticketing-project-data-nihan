package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,@Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }


    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        return  userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO user) {
       userRepository.save(userMapper.convertToEntity(user));
    }
      // hard delete
//    @Override
//    public void deleteByUserName(String username) {
//        userRepository.deleteByUserName(username);
//    }

    @Override
    public UserDTO update(UserDTO user) {
        //find current user
        User user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(), false);// this has id
        //Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(user); //updated user has no id
        //set id to the converted object
        convertedUser.setId(user1.getId());
        //save the updated user in the DB
        userRepository.save(convertedUser);

        return findByUserName(user.getUserName());// this will be needed for security for now we could have done the method void as well
        //we used this method because we are returning what is saved in DB
    }

    @Override
    public void delete(String username) {
        //go to DB and get that user with username
        //change the isDeleted field to true
        //save the object in the db
        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        if (checkIfUserCanBeDeleted(user)==true){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-"+ user.getId());//harold@manager.com-2 example
            userRepository.save(user);
        }


    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
       List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);
       return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    //since it's private we can pass DTO or entity. it does not have to get DTO from controller or  send dto back.
    //we only need this method here
    private boolean checkIfUserCanBeDeleted(User user) {

        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDTO(user));
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDTO(user));
                return taskDTOList.size() == 0;
            default:
                return true;
        }

    }
}
