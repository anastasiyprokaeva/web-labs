package com.example.WebLab1.Service;

import com.example.WebLab1.Exception.NotFoundException;
import com.example.WebLab1.Model.Project;
import com.example.WebLab1.Model.Task;
import com.example.WebLab1.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public Project createProject(Project newProject) {
        /*if(newProject.getStartDate().after(newProject.getEndDate())) {
            throw new RuntimeException();
        }*/
        List<Task> tasks = newProject.getTasks();
        if(tasks!=null) {
            for (int i = 0; i < tasks.size(); i++) {
                taskService.createTask(tasks.get(i));
            }
        }
        return projectRepository.save(newProject);
    }

    public Project readProject(Long id) throws NotFoundException {
        try {
            Project rp = projectRepository.findById(id);
            List<Task> tasks = taskService.readAllTasksByProject(id);
            rp.setTasks(tasks);
            return rp;
        }
       catch (DataAccessException e) {
            throw new NotFoundException();
       }
    }

    public List<Project> readAll() {
        List<Project> projects = projectRepository.findAll();
        List<Project> newProjects = new ArrayList<>();
        for(int i=0; i<projects.size(); i++) {
            newProjects.add(readProject(projects.get(i).getId()));
        }
        return newProjects;
    }

    public List<Project> readFilter(Date startDate, Date endDate) {
        /*if (startDate.after(endDate)) {
            throw new RuntimeException();
        }*/
        List<Project> projects = projectRepository.filter(startDate, endDate);
        List<Project> newProjects = new ArrayList<>();

        for(int i=0; i<projects.size(); i++) {
            newProjects.add(readProject(projects.get(i).getId()));
        }
        return newProjects;
        //  return projectRepository.filter(startDate, endDate);
    }

    public Project updateProject(Long id, Project newProject) throws NotFoundException {
       /* if (projectRepository.findById(newProject.getId()).getId() == null) {
            throw new NotFoundException();
        }*/
        try {
            newProject.setId(id);
            List<Task> newTasks = newProject.getTasks();
            for (int i = 0; i < newTasks.size(); i++) {
                taskService.updateTask(newTasks.get(i));
            }
            return projectRepository.update(newProject);
        }
        catch(DataAccessException e) {
            throw new NotFoundException();
        }
    }

    public void deleteProject(Long id) {
        Project p = projectRepository.findById(id);
        List<Task> tasks = p.getTasks();
        if(tasks!=null) {
            for (int i = 0; i < tasks.size(); i++) {
                taskService.deleteTask(tasks.get(i).getId());
            }
        }
        projectRepository.delete(id);
    }
}
