package repository;

/*

    Add, Update, and Delete tasks
    Mark a task as in progress or done
    List all tasks
    List all tasks that are done
    List all tasks that are not done
    List all tasks that are in progress

 */

import java.util.List;

import enums.Status;
import model.Task;

public interface TaskRepository {
    Task addTask(Task addedTask);
    Task updateTask(Task updatedTask);
    boolean deleteTask(Task deletedTask);
    Task changeTaskStatus(Long id, Status newStatus);
    List<Task> getAllTasks();
    List<Task> getTasksByStatus(Status status);
    void verifyIDVariable();
}
