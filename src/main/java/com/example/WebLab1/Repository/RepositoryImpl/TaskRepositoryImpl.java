package com.example.WebLab1.Repository.RepositoryImpl;

import com.example.WebLab1.Model.Project;
import com.example.WebLab1.Model.Task;
import com.example.WebLab1.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Task> rm = (resultSet, rowNum) -> {
        Task task = Task.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .plannedDate(resultSet.getDate("plannedDate"))
                .isCompleted(resultSet.getBoolean("isCompleted"))
                .build();
        return task;
    };

    @Override
    public Task save(Task newTask) {
        generateId(newTask);
        jdbcTemplate.update("INSERT into task (id, name, description, plannedDate, isCompleted) VALUES (?, ?, ?, ?, ?)",
                newTask.getId(),
                newTask.getName(), newTask.getDescription(), newTask.getPlannedDate(),
                newTask.isCompleted());
        return findById(newTask.getId());
    }

    @Override
    public Task findById(Long id) {
        return jdbcTemplate.queryForObject("select * from task where id = ?", rm, id);
    }

    @Override
    public List<Task> findAllByProjectId(Long id) {
        return jdbcTemplate.query("select * from task where projectId = ?", rm, id);
    }

    @Override
    public Task update(Task newTask) {
        jdbcTemplate.update("update task set name=?, description=?, plannedDate=?, isCompleted=? where id=?",
                newTask.getName(), newTask.getDescription(), newTask.getPlannedDate(), newTask.isCompleted(), newTask.getId());
        return findById(newTask.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from task where id=?", id);
    }

    private Task generateId(Task task){
        if (task.getId() == null) {
            Long id = jdbcTemplate.query("SELECT nextval('task_sequence')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getLong(1);
                        } else {
                            throw new SQLException();
                        }
                    });
            task.setId(id);
        }

        return task;
    }
}
