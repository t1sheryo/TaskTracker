package service;

/*

    Add, Update, and Delete tasks
    Mark a task as in progress or done
    List all tasks
    List all tasks that are done
    List all tasks that are not done
    List all tasks that are in progress

 */

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
