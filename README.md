## Task Tracker CLI
## 🎯 Overview
A simple, lightweight Task Tracker application implemented in Java with a Command Line Interface (CLI). The project is designed to help users manage and track their tasks, offering features like adding, updating, viewing, and deleting tasks, all while storing data in a JSON file, ensuring your tasks are saved between sessions.

## ✨ Features
Task Management: Create, update, view, and delete tasks.
Task Status Tracking: Tasks can be marked as Todo, In-Progress, or Done using an intuitive CLI.
Persistent Storage: Tasks are saved to a tasks.json file for persistent data storage.
Formatted Output: Displays tasks in a clean, well-organized table format within the terminal.
Minimal Dependencies: No external libraries are used, ensuring the project is simple and lightweight.
## 🚀 How to Run
Clone the repository:

```
git clone https://github.com/Farnam-Hs/Task-Tracker.git
cd Task-Tracker
Compile and run Application.java
```

## Commands
```
task-cli add "description" : Add a new task
task-cli update [id] "description" : Update a task
task-cli delete [id] : Delete a task
task-cli mark-todo [id] : Mark a task as Todo
task-cli mark-in-progress [id] : Mark a task as In-Progress
task-cli mark-done [id] : Mark a task as Done
task-cli list : List all tasks
task-cli list-todo : List all undone tasks
task-cli list-in-progress : List all In-Progress tasks
task-cli list-done : List all Done tasks
task-cli help : Shows list of available commands
task-cli quit : Quit the program
```

## 📘 Usage Example
```
+-------+------------------------------------------+--------------+------------------+------------------+
| ID    | Description                              | Status       | Created Time     | Updated Time     |
+-------+------------------------------------------+--------------+------------------+------------------+
| 1     | Finish documentation                     | TODO         | 2024-08-21 10:00 | 2024-08-21 10:00 |
| 2     | Implement CLI                            | IN_PROGRESS  | 2024-08-21 10:15 | 2024-08-21 11:00 |
+-------+------------------------------------------+--------------+------------------+------------------+
```

## 🚀 Установка

[![Скачать JAR](https://img.shields.io/badge/Скачать-JAR-зеленый?style=for-the-badge)](https://github.com/t1sheryo/TaskTracker/releases/download/v1.0/TaskTracker-1.0-SNAPSHOT-jar-with-dependencies.jar)
[![Скачать BAT](https://img.shields.io/badge/Скачать-BAT_файл-синий?style=for-the-badge)](https://github.com/t1sheryo/TaskTracker/releases/download/v1.0/start.bat)
[![Скачать исходники](https://img.shields.io/badge/Скачать-исходный_код-оранжевый?style=for-the-badge)](https://github.com/t1sheryo/TaskTracker/archive/refs/tags/v1.0.zip)
