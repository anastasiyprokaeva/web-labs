
package com.example.WebLab1.Repository.RepositoryImpl;

import com.example.WebLab1.Model.Project;
import com.example.WebLab1.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.ParametersSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Project> rm = (resultSet, rowNum) -> {
        Project project = Project.builder().id(resultSet.getLong("id")).name(resultSet.getString("name")).description(resultSet.getString("description")).startDate(resultSet.getDate("startDate")).endDate(resultSet.getDate("endDate")).build();
        return project;
    };
    
    @Override
    public Project save(Project newProject) {
        generateId(newProject);
        MapSqlParameterSource parametersSource = new MapSqlParameterSource();
        
        parametersSource.addValue("id", newProject.getId());
        parametersSource.addValue("name", newProject.getName());
        parametersSource.addValue("description", newProject.getDescription());
        parametersSource.addValue("startDate", newProject.getStartDate());
        parametersSource.addValue("endDate", newProject.getEndDate());
        jdbcTemplate.update(
        "INSERT into project (id, name, description, startDate, endDate) VALUES (:id, :name, :description, :startDate, :endDate)",
        parametersSource
        );
        return newProject;
    }
    
    @Override
    public Project findById(Long id) {
        MapSqlParameterSource parametersSource = new MapSqlParameterSource();
        parametersSource.addValue("id", id);
        return jdbcTemplate.queryForObject("select * from project where id = :id", parametersSource, rm);
    }
    
    @Override
    public List<Project> findAll() {
        return jdbcTemplate.query("select * from project", rm);
    }
    
    @Override
    public List<Project> filter(Date startDate, Date endDate) {
        MapSqlParameterSource parametersSource = new MapSqlParameterSource();
        parametersSource.addValue("startDate", startDate);
        parametersSource.addValue("endDate", endDate);
        return jdbcTemplate.query(
        "select * from project where startDate >= :startDate and endDate <= :endDate ",
        parametersSource,
        rm
        );
    }
    
    @Override
    public Project update(Project newProject) {
        MapSqlParameterSource parametersSource = new MapSqlParameterSource();
        parametersSource.addValue("name", newProject.getName());
        parametersSource.addValue("description", newProject.getDescription());
        parametersSource.addValue("startDate", newProject.getStartDate());
        parametersSource.addValue("endDate", newProject.getEndDate());
        parametersSource.addValue("id", newProject.getId());
        jdbcTemplate.update(
        "update project set name=:name, description=:description, startDate=:startDate, endDate=:endDate where id=:id",
        parametersSource
        );
        return newProject;
    }
    
    @Override
    public void delete(Long id) {
        MapSqlParameterSource parametersSource = new MapSqlParameterSource();
        parametersSource.addValue("id", id);
        jdbcTemplate.update("delete from project where id=:id", parametersSource);
    }
    
    private Project
    generateId(Project project) {
        if (project.getId() == null) {
            Long id = jdbcTemplate.query(
            "SELECT nextval('project_sequence')",
            rs -> {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException();
                }
            });
            project.setId(id);
        }
        
        return project;
    }
}
