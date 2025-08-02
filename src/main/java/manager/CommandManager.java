package manager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import controller.TaskController;
import enums.Command;
import model.Task;

public class CommandManager {
    private TaskController taskController;
    private static final ReentrantLock lock = new ReentrantLock();
    private static CommandManager instance;
    private final Scanner scanner;
    private static final String KEY_WORD = "task-cli";
    private static final String VALID_COMMAND_FORMAT = "Valid command format : task-cli <command> <id> <description>";
    private static final String BORDER = "+-------+------------------------------------------+--------------+------------------+------------------+";
    private static final String HEADER = "| ID    | Description                              | Status       | Created Time     | Updated Time     |";

    private CommandManager(TaskController taskController) {
        this.taskController = taskController;
        scanner = new Scanner(System.in);
    }

    public static CommandManager getInstance(TaskController taskController) {
        if(instance == null) {
            try{
                lock.lock();
                if(instance == null) {
                    instance = new CommandManager(taskController);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void run(){
        System.out.println("Application started!");

        verifyIDVariable();

        String command = "";
        while(scanner.hasNext()){
            command = scanner.next();
            if(!command.equalsIgnoreCase(KEY_WORD)){
                System.out.println("Wrong command! " + VALID_COMMAND_FORMAT);
                continue;
            }

            String line = scanner.nextLine().trim();
            String[] params = Arrays.stream(line.split("\""))
                    .map(String::trim)
                    .toArray(String[]::new);

            String[] commands = Arrays.stream(params[0].split(" "))
                    .map(String::trim)
                    .toArray(String[]::new);

            Queue<String> q = new LinkedList<>(Arrays.asList(commands));

            command = q.poll();
            assert command != null;
            if(!q.isEmpty() && command.equalsIgnoreCase("list")){
                String secondCommandWord = q.poll();
                command = command + " " + secondCommandWord;
            }
            if(!validateCommand(command)){
                System.out.println("Wrong command! " + VALID_COMMAND_FORMAT);
                continue;
            }

            command = command.toLowerCase();
            if(distribute(command, params, commands)){
                break;
            }
        }
        System.out.println("Application finished!");
    }

    private boolean distribute(String command, String[] params, String[] commands){
        switch (command){
            case "add":
                addTask(params[1]);
                System.out.println(BORDER);
                break;

            case "update":
                updateTask(Long.parseLong(commands[1]), params[1]);
                System.out.println(BORDER);
                break;

            case "delete":
                deleteTask(Long.parseLong(commands[1]));
                System.out.println(BORDER);
                break;

            case "mark-in-progress":
                markInProgress(Long.parseLong(commands[1]));
                System.out.println(BORDER);
                break;

            case "mark-done":
                markDone(Long.parseLong(commands[1]));
                System.out.println(BORDER);
                break;

            case "list":
                System.out.println(HEADER);
                listAll();
                System.out.println(BORDER);
                break;

            case "list done":
                System.out.println(HEADER);
                listDone();
                System.out.println(BORDER);
                break;

            case "list todo":
                System.out.println(HEADER);
                listTODO();
                System.out.println(BORDER);
                break;

            case "list in-progress":
                System.out.println(HEADER);
                listInProgress();
                System.out.println(BORDER);
                break;

            case "help":
                printHelpMenu();
                System.out.println(BORDER);
                break;

            case "quit":
                return true;
        }

        return false;
    }

    private void addTask(String description){
        Task task = Task.builder()
                .description(description)
                .build();
        taskController.addTask(task);
    }

    private void updateTask(Long id, String description){
        Task task = Task.builder()
                .id(id)
                .description(description)
                .build();
        taskController.updateTask(task);
    }

    private void deleteTask(Long id){
        Task task = Task.builder()
                .id(id)
                .build();
        taskController.deleteTask(task);
    }

    private void markInProgress(Long id){
        taskController.markTaskAsInProgress(id);
    }

    private void markDone(Long id){
        taskController.markTaskAsDone(id);
    }

    private void listAll(){
        taskController.getAllTasks();
    }

    private void listDone(){
        taskController.getDoneTasks();
    }

    private void listTODO(){
        taskController.getNotDoneTasks();
    }

    private void listInProgress(){
        taskController.getInProgressTasks();
    }

    private boolean validateCommand(String command){
        for(Command s : Command.values()){
            if(s.getStringValue().equalsIgnoreCase(command)){
                return true;
            }
        }

        return false;
    }

    private void verifyIDVariable(){
        taskController.verifyIDVariable();
    }

    private void printHelpMenu() {
        String help = """
                - add [description] : Add a new task
                - update [id] [description] : Update a task
                - delete [id] : Delete a task
                - mark-todo [id] : Mark a task as Todo
                - mark-in-progress [id] : Mark a task as In-Progress
                - mark-done [id] : Mark a task as Done
                - list : List all tasks
                - list-todo : List all Todo tasks
                - list-in-progress : List all In-Progress tasks
                - list-done : List all Done tasks
                - exit : Exit the program
                """;
        System.out.println(help);
    }
}
