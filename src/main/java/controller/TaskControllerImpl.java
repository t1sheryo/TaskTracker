package controller;

import model.Task;
import service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class TaskControllerImpl implements TaskController {
    private TaskService taskService;
    private static final ReentrantLock lock = new ReentrantLock();
    private static TaskController instance;
    private static final String TASK_FORMAT = "| %-5s | %-40s | %-12s | %-16s | %-16s |%n";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private TaskControllerImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    public static TaskController getInstance(TaskService taskService) {
        if(instance == null) {
            try{
                lock.lock();
                if(instance == null) {
                    instance = new TaskControllerImpl(taskService);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
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
            System.out.printf(
                    TASK_FORMAT,
                    task.getId(),
                    task.getDescription(),
                    task.getStatus().toString(),
                    task.getCreatedAt().format(DATE_FORMATTER),
                    task.getUpdatedAt().format(DATE_FORMATTER));
        });

        return lst;
    }

    @Override
    public List<Task> getDoneTasks() {
        List<Task> lst = taskService.getDoneTasks();

        lst.forEach(task -> {
            System.out.printf(
                    TASK_FORMAT,
                    task.getId(),
                    task.getDescription(),
                    task.getStatus().toString(),
                    task.getCreatedAt().format(DATE_FORMATTER),
                    task.getUpdatedAt().format(DATE_FORMATTER));
        });

        return lst;
    }

    @Override
    public List<Task> getNotDoneTasks() {
        List<Task> lst = taskService.getNotDoneTasks();

        lst.forEach(task -> {
            System.out.printf(
                    TASK_FORMAT,
                    task.getId(),
                    task.getDescription(),
                    task.getStatus().toString(),
                    task.getCreatedAt().format(DATE_FORMATTER),
                    task.getUpdatedAt().format(DATE_FORMATTER));
        });

        return lst;
    }

    @Override
    public List<Task> getInProgressTasks() {
        List<Task> lst = taskService.getInProgressTasks();

        lst.forEach(task -> {
            System.out.printf(
                    TASK_FORMAT,
                    task.getId(),
                    task.getDescription(),
                    task.getStatus().toString(),
                    task.getCreatedAt().format(DATE_FORMATTER),
                    task.getUpdatedAt().format(DATE_FORMATTER));
        });

        return lst;
    }

    @Override
    public void verifyIDVariable(){
        taskService.verifyIDVariable();
    }
}
