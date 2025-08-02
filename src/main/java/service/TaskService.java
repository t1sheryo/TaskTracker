package service;

import java.util.List;

import model.Task;

public interface TaskService {
    Task addTask(Task addedTask);
    Task updateTask(Task updatedTask);
    boolean deleteTask(Task deletedTask);
    Task markTaskAsInProgress(Long id);
    Task markTaskAsDone(Long id);
    List<Task> getAllTasks();
    List<Task> getDoneTasks();
    List<Task> getNotDoneTasks();
    List<Task> getInProgressTasks();
    void verifyIDVariable();
}
