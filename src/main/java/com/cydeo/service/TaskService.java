package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;

import java.util.List;

public interface TaskService {

    //save, update, delete, show all tasks, show certain taskts

    List<TaskDTO> listAllTasks();

    void save(TaskDTO dto);

    void update(TaskDTO dto);

    void delete(Long id);//only unique field is id for task

    TaskDTO findById(Long id);

    int totalNonCompletedTask(String projectCode);
    int totalCompletedTask(String projectCode);


}
