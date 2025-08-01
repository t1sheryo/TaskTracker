package controller;

import model.Task;
import service.TaskService;

import java.util.List;
import java.util.Scanner;

public class TaskControllerImpl implements TaskController {
    private TaskService taskService;
    private final Scanner scanner;

    public TaskControllerImpl(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Task addTask(Task addedTask) {
        // FIXME: display task
        return taskService.addTask(addedTask);
    }

    @Override
    public Task updateTask(Task updatedTask) {
        // FIXME: display task
        return taskService.updateTask(updatedTask);
    }

    @Override
    public boolean deleteTask(Task deletedTask) {
        // FIXME: display task
        return taskService.deleteTask(deletedTask);
    }

    @Override
    public Task markTaskAsInProgress(Long id) {
        // FIXME: display task
        return taskService.markTaskAsInProgress(id);
    }

    @Override
    public Task markTaskAsDone(Long id) {
        // FIXME: display task
        return taskService.markTaskAsDone(id);
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> lst = taskService.getAllTasks();

        lst.forEach(task -> {
            System.out.println("Task : id: " + task.getId() +
                    " status: " + task.getStatus() +
                    " createdAt: " + task.getCreatedAt() +
                    " updatedAt: " + task.getUpdatedAt());
        });

        return lst;
    }

    @Override
    public List<Task> getDoneTasks() {
        List<Task> lst = taskService.getDoneTasks();

        lst.forEach(task -> {
                    System.out.println("Task : id: " + task.getId() +
                            " status: " + task.getStatus() +
                            " createdAt: " + task.getCreatedAt() +
                            " updatedAt: " + task.getUpdatedAt());
                });

        return lst;
    }

    @Override
    public List<Task> getNotDoneTasks() {
        List<Task> lst = taskService.getNotDoneTasks();

        lst.forEach(task -> {
            System.out.println("Task : id: " + task.getId() +
                    " status: " + task.getStatus() +
                    " createdAt: " + task.getCreatedAt() +
                    " updatedAt: " + task.getUpdatedAt());
        });

        return lst;
    }

    @Override
    public List<Task> getInProgressTasks() {
        List<Task> lst = taskService.getInProgressTasks();

        lst.forEach(task -> {
            System.out.println("Task : id: " + task.getId() +
                    " status: " + task.getStatus() +
                    " createdAt: " + task.getCreatedAt() +
                    " updatedAt: " + task.getUpdatedAt());
        });

        return lst;
    }
}
