package repository;

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
