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
        return null;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        return null;
    }

    @Override
    public boolean deleteTask(Task deletedTask) {
        return false;
    }

    @Override
    public Task markTaskAsInProgress(Long id) {
        return null;
    }

    @Override
    public Task markTaskAsDone(Long id) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }

    @Override
    public List<Task> getDoneTasks() {
        return List.of();
    }

    @Override
    public List<Task> getNotDoneTasks() {
        return List.of();
    }

    @Override
    public List<Task> getInProgressTasks() {
        return List.of();
    }
}
