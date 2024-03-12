package com.example.WebLab1.CreateTable;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectTable  {
    private final JdbcTemplate jdbcTemplate;


    @EventListener(ApplicationReadyEvent.class)
    public void createProject() {
        this.jdbcTemplate.execute("create table if not exists project " +
                "(" +
                "id integer primary key, " +
                "name varchar(100) not null unique, " +
                "description varchar, " +
                "startDate date not null DEFAULT CURRENT_DATE, " +
                "endDate date" +
                ")"
        );
        createSequence();
       // test();
    }

    public void createSequence() {
        jdbcTemplate.execute("create sequence if not exists project_sequence start with 1");
    }

}
