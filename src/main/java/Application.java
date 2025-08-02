import controller.TaskControllerImpl;
import manager.CommandManager;
import repository.TaskRepositoryImpl;
import service.TaskServiceImpl;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("How do you want to name your file?\nSample: \"filename.json\"");
        String filename = new Scanner(System.in).next();
        CommandManager.getInstance(
                TaskControllerImpl.getInstance(
                        TaskServiceImpl.getInstance(
                                TaskRepositoryImpl.getInstance(filename)
                        )
                )
        ).run();
    }
}
