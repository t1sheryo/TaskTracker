package repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.nio.file.Files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import exception.TaskNotFoundException;
import lombok.AllArgsConstructor;

import enums.Status;
import model.Task;

public class TaskRepositoryImpl implements TaskRepository {
    private static TaskRepositoryImpl instance;
    private static final ReentrantLock lock;
    private final String filepath;
    private final Path path;
    private static final String FILEPATH_PREFIX = "\\src\\main\\resources\\";
    private static final ObjectMapper mapper;

    static {
        lock = new ReentrantLock();
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty format
    }

    private TaskRepositoryImpl(String filename) {
        this.filepath = FILEPATH_PREFIX + filename;
        this.path = Path.of(filepath);
    }

    public static TaskRepositoryImpl getInstance(String filename) {
        if(instance == null) {
            try{
                lock.lock();
                if(instance == null) {
                    instance = new TaskRepositoryImpl(filename);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    // class to hold List of all object and necessary object
    @AllArgsConstructor
    private static class dataHelper{
        List<Task> tasks;
        Task task;
    }

    @Override
    public Task addTask(Task addedTask) {
        checkFileExistenceAndCreate();

        addedTask.setStatus(Status.TODO);
        addedTask.setCreatedAt(LocalDateTime.now());
        addedTask.setUpdatedAt(LocalDateTime.now());

        writeObjectToFile(addedTask);

        return addedTask;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        dataHelper data = getTaskById(updatedTask.getId());

        updatedTask.setStatus(Status.TODO);
        updatedTask.setUpdatedAt(LocalDateTime.now());

        data.task = updatedTask;
        writeListOfObjectToFile(data.tasks);

        return data.task;
    }

    @Override
    public boolean deleteTask(Task deletedTask) {
        List<Task> tasks = getAllTasks();

        if(!tasks.contains(deletedTask)) {
            writeListOfObjectToFile(tasks);
            return false;
        }

        tasks.remove(deletedTask);
        writeListOfObjectToFile(tasks);

        return true;
    }

    @Override
    public Task changeTaskStatus(Long id, Status newStatus) {
        checkFileExistenceAndCreate();

        dataHelper data = getTaskById(id);
        data.task.setStatus(newStatus);
        writeListOfObjectToFile(data.tasks);

        return data.task;
    }

    @Override
    public List<Task> getAllTasks() {
        checkFileExistenceAndCreate();

        return readDataFromFile();
    }

    @Override
    public List<Task> getTasksByStatus(Status status) {
        checkFileExistenceAndCreate();

        List<Task> tasks = readDataFromFile();

        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    private void checkFileExistenceAndCreate() {
        if(!Files.exists(path)){
            try{
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private dataHelper getTaskById(Long id) {
        checkFileExistenceAndCreate();

        List<Task> tasks = readDataFromFile();

        return new dataHelper(
                tasks,
                tasks.stream()
                    .filter(task -> task.getId().equals(id))
                    .findAny()
                    .orElseThrow(() -> new TaskNotFoundException("No task with id " + id))
        );
    }

    private void writeObjectToFile(Task taskToWrite) {
        try {
            String json = mapper.writeValueAsString(taskToWrite);

            Files.writeString(
                    path,
                    json,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeListOfObjectToFile(List<Task> tasksToWrite) {
        try {
            String json = mapper.writeValueAsString(tasksToWrite);

            Files.writeString(
                    path,
                    json,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Task> readDataFromFile() {
        try {
            String json = Files.readString(path); // reads whole file and puts it in single string

            return convertJsonDataToPOJO(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Task> convertJsonDataToPOJO(String jsonData) {
        try {
            return mapper.readValue(
                    jsonData,
                    new TypeReference<List<Task>>() {
                    }
            );
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
