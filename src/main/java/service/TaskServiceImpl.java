package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import enums.Status;
import model.Task;
import repository.TaskRepository;

public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private static final ReentrantLock lock = new ReentrantLock();
    private static TaskServiceImpl instance;

    private TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public static TaskServiceImpl getInstance(TaskRepository taskRepository) {
        if(instance == null) {
            try{
                lock.lock();
                if(instance == null) {
                    instance = new TaskServiceImpl(taskRepository);
                }
            } finally {
                lock.unlock();
            }
        }

        return instance;
    }

    @Override
    public Task addTask(Task addedTask) {
        return taskRepository.addTask(addedTask);
    }

    @Override
    public Task updateTask(Task updatedTask) {
        return taskRepository.updateTask(updatedTask);
    }

    @Override
    public boolean deleteTask(Task deletedTask) {
        return taskRepository.deleteTask(deletedTask);
    }

    @Override
    public Task markTaskAsInProgress(Long id) {
        return taskRepository.changeTaskStatus(id, Status.IN_PROGRESS);
    }

    @Override
    public Task markTaskAsDone(Long id) {
        return taskRepository.changeTaskStatus(id, Status.DONE);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    @Override
    public List<Task> getDoneTasks() {
        return taskRepository.getTasksByStatus(Status.DONE);
    }

    @Override
    public List<Task> getNotDoneTasks() {
        List<Task> tasks = new ArrayList<>(taskRepository.getTasksByStatus(Status.TODO));
        tasks.addAll(taskRepository.getTasksByStatus(Status.IN_PROGRESS));
        return tasks;
    }

    @Override
    public List<Task> getInProgressTasks() {
        return taskRepository.getTasksByStatus(Status.IN_PROGRESS);
    }

    @Override
    public void verifyIDVariable(){
        taskRepository.verifyIDVariable();
    }
}
