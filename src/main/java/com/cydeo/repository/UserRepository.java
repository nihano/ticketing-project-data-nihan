package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    //We commented out WHERE clause in User entity because it was giving error in project create when try to complete project
    //because we wanted to delete one user (before we completed his tasks) after deleting user we wanted complete project
    //but since project has 4 related tasks and user was is_deleted=true in DB, it could not find the user id
    //because we had WHERE is_deleted=false on top of the User entity.
    //we still need to show only non deleted users in the list so we added that condition here in UserRepository one by one
    //Using WHERE clause in entities can be dangerous.
    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);
    //get user based on username
    User findByUserNameAndIsDeleted(String username, Boolean deleted);
    @Transactional
    void deleteByUserName(String username);

    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);



}
