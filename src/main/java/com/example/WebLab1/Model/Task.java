package com.example.WebLab1.Model;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long id;
    private String name;
    private String description;
    private Date plannedDate;
    @Builder.Default
    private boolean isCompleted = false;
}
