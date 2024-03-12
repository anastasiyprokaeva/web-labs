package com.example.WebLab1.Model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String description;
    @Builder.Default
    private Date startDate = new Date();
    private Date endDate;
    private List<Task> tasks;
}
