package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exception.TaskNotFoundException;
import lombok.AllArgsConstructor;

import enums.Status;
import model.Task;

public class TaskRepositoryImpl implements TaskRepository {
    private static TaskRepositoryImpl instance;
    private static final ReentrantLock lock;
    private final Path path;
    private static final String DIRECTORY = System.getProperty("user.dir") + "\\tmp\\";
    private static final ObjectMapper mapper;

    static {
        lock = new ReentrantLock();
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private TaskRepositoryImpl(String filename) {
        try {
            Files.createDirectories(Path.of(DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.path = Path.of(DIRECTORY + filename);
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

    @AllArgsConstructor
    private static class dataHelper{
        List<Task> tasks;
        Task task;
    }

    @Override
    public Task addTask(Task addedTask) {
        checkFileExistenceAndCreate();

        addedTask.initializeId();
        addedTask.setStatus(Status.TODO);
        addedTask.setCreatedAt(LocalDateTime.now());
        addedTask.setUpdatedAt(LocalDateTime.now());

        List<Task> existingTasks = readDataFromFile();
        existingTasks.add(addedTask);
        
        writeObjectsToFile(existingTasks);

        return addedTask;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        dataHelper data = getTaskById(updatedTask.getId());

        data.task.setDescription(updatedTask.getDescription());
        data.task.setStatus(Status.TODO);
        data.task.setUpdatedAt(LocalDateTime.now());

        writeObjectsToFile(data.tasks);

        return data.task;
    }

    @Override
    public boolean deleteTask(Task deletedTask) {
        List<Task> tasks = getAllTasks();

        boolean deleted = false;
        for(int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId().equals(deletedTask.getId())) {
                tasks.remove(i);
                deleted = true;
                break;
            }
        }

        writeObjectsToFile(tasks);

        return deleted;
    }

    @Override
    public Task changeTaskStatus(Long id, Status newStatus) {
        checkFileExistenceAndCreate();

        dataHelper data = getTaskById(id);
        data.task.setStatus(newStatus);
        writeObjectsToFile(data.tasks);

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

    private void writeObjectsToFile(List<Task> tasksToWrite) {
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
            String json = Files.readString(path);

            return convertJsonDataToPOJO(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Task> convertJsonDataToPOJO(String json) {
        try {
            if(json.isBlank()) return new ArrayList<>();
            if (json.trim().startsWith("[")) {
                return mapper.readValue(json, new TypeReference<List<Task>>() {});
            } else {
                Task singleTask = mapper.readValue(json, Task.class);
                return new ArrayList<>(Collections.singletonList(singleTask));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void verifyIDVariable(){
        Optional<Task> task = getAllTasks().stream()
                .max(Comparator.comparingLong(Task::getId));

        Long maxId = 0L;
        if(task.isPresent()){
            maxId = task.get().getId();
        }

        Task.updateIDVariable(maxId);
    }
}
