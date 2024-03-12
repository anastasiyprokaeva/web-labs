package com.example.WebLab1.Service;

import com.example.WebLab1.Exception.NotFoundException;
import com.example.WebLab1.Model.Project;
import com.example.WebLab1.Model.Task;
import com.example.WebLab1.Repository.ProjectRepository;
import com.example.WebLab1.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public Task createTask(Task newTask) {
        return taskRepository.save(newTask);
    }

    public Task readTask(Long id) throws NotFoundException {
        try {
            Task tp = taskRepository.findById(id);
            return tp;
        }
        catch(DataAccessException e) {
            throw new NotFoundException();
        }
    }

    public List<Task> readAllTasksByProject(Long id) throws NotFoundException {
        try {
            return taskRepository.findAllByProjectId(id);
        }
        catch(DataAccessException e) {
            throw new NotFoundException();
        }
    }

    public Task updateTask(Task newTask) {
        if (taskRepository.findById(newTask.getId()).getId() == null) {
            throw new NotFoundException();
        }
        return taskRepository.update(newTask);
    }

    public void deleteTask(Long id) {
        taskRepository.delete(id);
    }
}
