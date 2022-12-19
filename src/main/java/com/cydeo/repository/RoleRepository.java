package com.cydeo.repository;

import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
   /* Here we extend from JPARepository because this is Repository
   interface and, we will work with DB and, we pass Role and Long
   as data type because we work with entity not DTO in DB.
    */

    //give me the role based on the description
    Role findAllByDescription(String description); //Derived Query, we can put Optional as well but, we put Role




}
