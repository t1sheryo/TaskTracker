package model;


import java.time.LocalDateTime;

import lombok.*;

import enums.Status;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public void initializeId(){
        id = ++ID_VARIABLE;
    }

    public static void updateIDVariable(Long id){
        ID_VARIABLE = id;
    }
}

