package com.example.WebLab1.CreateTable;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class TaskTable {
    private final JdbcTemplate JdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void CreateTask() {
        this.JdbcTemplate.execute("create table if not exists task " +
                "(" +
                "id integer primary key, " +
                "name varchar(100) not null, " +
                "description varchar, " +
                "plannedDate date, " +
                "isCompleted boolean DEFAULT false, " +
                "projectId integer REFERENCES project (id)" +
                ")"
        );
        createSequence();
    }

    public void createSequence() {
        JdbcTemplate.execute("create sequence if not exists task_sequence start with 1");
    }
}
