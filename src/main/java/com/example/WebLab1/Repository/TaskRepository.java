package com.example.WebLab1.Repository;

import com.example.WebLab1.Model.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task newTask);
    Task findById(Long id);
    List<Task> findAllByProjectId(Long id);
    Task update(Task newTask);
    void delete(Long id);
}
