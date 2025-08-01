package model;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import enums.Status;

/*
Each task should have the following properties:

id: A unique identifier for the task
description: A short description of the task
status: The status of the task (todo, in-progress, done)
createdAt: The date and time when the task was created
updatedAt: The date and time when the task was last updated
Make sure to add these properties to the JSON file when adding a new task and update them when updating a task.

 */

@Setter
@Getter
public class Task {
    private static Long ID_VARIABLE = 0L;

    public Task(
            String description,
            Status status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        id = ++ID_VARIABLE;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Long id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

