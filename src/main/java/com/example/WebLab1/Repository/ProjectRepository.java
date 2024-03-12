package com.example.WebLab1.Repository;

import com.example.WebLab1.Model.Project;

import java.util.Date;
import java.util.List;

public interface ProjectRepository {
    Project save(Project newProject);
    Project findById(Long id);
    List<Project> findAll();
    List<Project> filter(Date startDate, Date endDate);
    Project update(Project newProject);
    void delete(Long id);

}
