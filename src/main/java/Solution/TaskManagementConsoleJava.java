package Solution;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main console application for task management.
 * Implements command interface with proper input validation and error handling.
 */
public class TaskManagementConsoleJava
{
    private final TaskManager taskManager;
    private final CommandParser commandParser;
    private final FileHandler fileHandler;
    private final Scanner scanner;
    private boolean running;
    
    /**
     * Initializes the application components.
     */
    public TaskManagementConsoleJava()
    {
        this.taskManager = new TaskManager();
        this.commandParser = new CommandParser();
        this.fileHandler = new FileHandler();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }
    
    /**
     * Main entry point of the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args)
    {
        TaskManagementConsoleJava app = new TaskManagementConsoleJava();
        app.run();
    }
    
    /**
     * Runs the main application loop.
     */
    public void run()
    {
        printWelcomeMessage();
        loadTasksFromFile();
        
        while (running)
        {
            printPrompt();
            String input = scanner.nextLine();
            
            try
            {
                processCommand(input);
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        saveTasksToFile();
        scanner.close();
        System.out.println("Application terminated. Goodbye!");
    }
    
    /**
     * Processes a user command.
     * @param input Raw user input
     */
    private void processCommand(String input)
    {
        CommandParser.ParsedCommand parsed = commandParser.parse(input);
        String command = parsed.getCommand();
        String args = parsed.getArguments();
        
        switch (command)
        {
            case "create":
            case "add":
                handleCreateCommand(args);
                break;
                
            case "list":
            case "show":
                handleListCommand(args);
                break;
                
            case "update":
            case "change":
                handleUpdateCommand(args);
                break;
                
            case "remove":
            case "delete":
                handleRemoveCommand(args);
                break;
                
            case "clear":
            case "cls":
                handleClearCommand();
                break;
                
            case "help":
                printHelp();
                break;
                
            case "exit":
            case "quit":
                handleExitCommand();
                break;
                
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Type 'help' for available commands.");
                break;
        }
    }
    
    /**
     * Handles task creation command.
     * @param args Command arguments
     */
    private void handleCreateCommand(String args)
    {
        try
        {
            String[] validatedArgs = commandParser.validateCreateArgs(args);
            String description = validatedArgs[0];
            String priority = validatedArgs[1];
            String dueDate = validatedArgs[2];
            
            Task task = taskManager.createTask(description, priority, dueDate);
            System.out.println("Task created successfully:");
            System.out.println(task);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Create command error: " + e.getMessage());
            System.out.println("Usage: create \"description\", priority, yyyy-MM-dd");
            System.out.println("Example: create \"Complete assignment\", HIGH, 2024-12-31");
        }
    }
    
    /**
     * Handles task listing command.
     * @param args Command arguments
     */
    private void handleListCommand(String args)
    {
        List<Task> tasks;
        
        if (args == null || args.trim().isEmpty())
        {
            tasks = taskManager.getAllTasks();
        }
        else
        {
            try
            {
                tasks = taskManager.getTasksByStatus(args);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("List command error: " + e.getMessage());
                System.out.println("Usage: list [status]");
                System.out.println("Status values: To Do, Doing, Done");
                return;
            }
        }
        
        if (tasks.isEmpty())
        {
            System.out.println("No tasks found.");
        }
        else
        {
            System.out.println("\n=== Tasks ===");
            System.out.println(taskManager.getTaskCounts());
            System.out.println("-".repeat(80));
            
            for (Task task : tasks)
            {
                System.out.println(task);
            }
            
            System.out.println("-".repeat(80));
        }
    }
    
    /**
     * Handles task update command.
     * @param args Command arguments
     */
    private void handleUpdateCommand(String args)
    {
        if (args == null || args.trim().isEmpty())
        {
            System.out.println("Update command requires subcommand: status, desc, priority, or due");
            System.out.println("Example: update status 1 Doing");
            return;
        }
        
        String[] parts = args.split("\\s+", 3);
        
        if (parts.length < 3)
        {
            System.out.println("Update command requires: subcommand, taskId, value");
            return;
        }
        
        String subcommand = parts[0].toLowerCase();
        String taskIdStr = parts[1];
        String value = parts[2];
        
        try
        {
            int taskId = Integer.parseInt(taskIdStr);
            Task task = null;
            
            switch (subcommand)
            {
                case "status":
                    task = taskManager.updateTaskStatus(taskId, value);
                    System.out.println("Task status updated:");
                    break;
                    
                case "desc":
                case "description":
                    task = taskManager.updateTaskDescription(taskId, value);
                    System.out.println("Task description updated:");
                    break;
                    
                case "priority":
                    task = taskManager.updateTaskPriority(taskId, value);
                    System.out.println("Task priority updated:");
                    break;
                    
                case "due":
                case "duedate":
                    task = taskManager.updateTaskDueDate(taskId, value);
                    System.out.println("Task due date updated:");
                    break;
                    
                default:
                    System.out.println("Unknown update subcommand: " + subcommand);
                    System.out.println("Valid subcommands: status, desc, priority, due");
                    return;
            }
            
            if (task != null)
            {
                System.out.println(task);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Error: Task ID must be a number");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Update error: " + e.getMessage());
        }
    }
    
    /**
     * Handles task removal command.
     * @param args Command arguments
     */
    private void handleRemoveCommand(String args)
    {
        try
        {
            String taskIdStr = commandParser.validateRemoveArgs(args);
            int taskId = Integer.parseInt(taskIdStr);
            
            boolean removed = taskManager.removeTask(taskId);
            
            if (removed)
            {
                System.out.println("Task " + taskId + " removed successfully.");
            }
            else
            {
                System.out.println("Task " + taskId + " not found.");
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Remove command error: " + e.getMessage());
            System.out.println("Usage: remove taskId");
            System.out.println("Example: remove 1");
        }
    }
    
    /**
     * Handles clear screen command.
     * Clears the console window while preserving all task data in memory.
     */
    private void handleClearCommand()
    {
        clearConsole();
        System.out.println("Console cleared. Task data preserved in memory.");
    }
    
    /**
     * Handles exit command.
     */
    private void handleExitCommand()
    {
        System.out.println("Exiting application...");
        running = false;
    }
    
    /**
     * Clears the console screen in a cross-platform manner.
     * Works on Windows, Linux, and macOS terminals.
     */
    private void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("windows"))
            {
                // Windows command
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                // Unix/Linux/macOS command
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch (final Exception e)
        {
            // Fallback: print multiple newlines if native clearing fails
            for (int i = 0; i < 50; i++)
            {
                System.out.println();
            }
            System.out.println("Console cleared.");
        }
    }
    
    /**
     * Loads tasks from file if it exists.
     */
    private void loadTasksFromFile()
    {
        try
        {
            if (fileHandler.fileExists("tasks.csv"))
            {
                List<Task> tasks = fileHandler.loadTasksFromFile();
                // Since we can't directly add to TaskManager, we need to clear and recreate
                taskManager.clearAllTasks();
                for (Task task : tasks)
                {
                    // Recreate each task (IDs will be preserved from CSV)
                    taskManager.createTask(
                        task.getDescription(),
                        task.getPriority().name(),
                        task.getDueDate().toString()
                    );
                    // Update status
                    taskManager.updateTaskStatus(task.getId(), task.getStatus().toString());
                }
                System.out.println("Tasks loaded from file.");
            }
        }
        catch (IOException e)
        {
            System.out.println("Warning: Could not load tasks from file: " + e.getMessage());
        }
    }
    
    /**
     * Saves tasks to file.
     */
    private void saveTasksToFile()
    {
        try
        {
            List<Task> tasks = taskManager.getAllTasks();
            fileHandler.saveTasksToFile(tasks);
            System.out.println("Tasks saved to file.");
        }
        catch (IOException e)
        {
            System.out.println("Warning: Could not save tasks to file: " + e.getMessage());
        }
    }
    
    /**
     * Prints welcome message and instructions.
     */
    private void printWelcomeMessage()
    {
        System.out.println("========================================");
        System.out.println("   TASK MANAGEMENT CONSOLE APPLICATION");
        System.out.println("   Version: V-1.0.0");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Type 'help' for available commands.");
        System.out.println();
    }
    
    /**
     * Prints command prompt.
     */
    private void printPrompt()
    {
        System.out.print("> ");
        System.out.flush();
    }
    
    /**
     * Prints help information.
     */
    private void printHelp()
    {
        System.out.println("\n=== Available Commands ===");
        System.out.println();
        System.out.println("CREATE/ADD - Create a new task");
        System.out.println("  Usage: create \"description\", priority, yyyy-MM-dd");
        System.out.println("  Example: create \"Complete Java assignment\", HIGH, 2024-12-31");
        System.out.println("  Priorities: LOW, MEDIUM, HIGH");
        System.out.println();
        System.out.println("LIST/SHOW - List tasks");
        System.out.println("  Usage: list [status]");
        System.out.println("  Example: list Doing");
        System.out.println("  Status values: To Do, Doing, Done");
        System.out.println();
        System.out.println("UPDATE/CHANGE - Update a task");
        System.out.println("  Usage: update [status|desc|priority|due] taskId value");
        System.out.println("  Examples:");
        System.out.println("    update status 1 Doing");
        System.out.println("    update desc 1 \"New description\"");
        System.out.println("    update priority 1 MEDIUM");
        System.out.println("    update due 1 2024-12-31");
        System.out.println();
        System.out.println("REMOVE/DELETE - Remove a task");
        System.out.println("  Usage: remove taskId");
        System.out.println("  Example: remove 1");
        System.out.println();
        System.out.println("CLEAR/CLS - Clear the console screen");
        System.out.println("  Usage: clear");
        System.out.println("  Note: This only clears the display, task data remains in memory");
        System.out.println();
        System.out.println("HELP - Show this help message");
        System.out.println("EXIT/QUIT - Exit the application");
        System.out.println("\n========================================");
    }
}