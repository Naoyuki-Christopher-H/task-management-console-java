package Solution;

import java.util.Arrays;

/**
 * Parses and validates user commands.
 * Separates command parsing logic from application control.
 */
public class CommandParser
{
    /**
     * Parses a command string into command type and arguments.
     * @param input User input string
     * @return Parsed command object
     * @throws IllegalArgumentException if command format is invalid
     */
    public ParsedCommand parse(String input)
    {
        if (input == null || input.trim().isEmpty())
        {
            throw new IllegalArgumentException("Command cannot be empty");
        }
        
        String trimmedInput = input.trim();
        String[] parts = trimmedInput.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";
        
        return new ParsedCommand(command, arguments);
    }
    
    /**
     * Validates create command arguments.
     * @param args Command arguments
     * @return Array of validated arguments [description, priority, dueDate]
     * @throws IllegalArgumentException if arguments are invalid
     */
    public String[] validateCreateArgs(String args)
    {
        if (args == null || args.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Create command requires: description, priority, dueDate (yyyy-MM-dd)");
        }
        
        // Split by comma, but preserve commas in quotes
        String[] parts = args.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        if (parts.length < 3)
        {
            throw new IllegalArgumentException(
                "Create command requires: description, priority, dueDate (yyyy-MM-dd)");
        }
        
        String description = parts[0].trim();
        String priority = parts[1].trim().toUpperCase();
        String dueDate = parts[2].trim();
        
        // Remove quotes if present
        if (description.startsWith("\"") && description.endsWith("\""))
        {
            description = description.substring(1, description.length() - 1);
        }
        
        // Validate description
        if (description.isEmpty())
        {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        // Validate priority
        try
        {
            Task.Priority.fromString(priority);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        return new String[]{description, priority, dueDate};
    }
    
    /**
     * Validates update status command arguments.
     * @param args Command arguments
     * @return Array of validated arguments [taskId, status]
     * @throws IllegalArgumentException if arguments are invalid
     */
    public String[] validateUpdateStatusArgs(String args)
    {
        if (args == null || args.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Update status command requires: taskId, status");
        }
        
        String[] parts = args.split("\\s+", 2);
        
        if (parts.length < 2)
        {
            throw new IllegalArgumentException(
                "Update status command requires: taskId, status");
        }
        
        String taskIdStr = parts[0].trim();
        String status = parts[1].trim();
        
        // Validate task ID
        try
        {
            Integer.parseInt(taskIdStr);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Task ID must be a number");
        }
        
        // Validate status
        try
        {
            Task.Status.fromString(status);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        return new String[]{taskIdStr, status};
    }
    
    /**
     * Validates remove command arguments.
     * @param args Command arguments
     * @return Validated task ID string
     * @throws IllegalArgumentException if arguments are invalid
     */
    public String validateRemoveArgs(String args)
    {
        if (args == null || args.trim().isEmpty())
        {
            throw new IllegalArgumentException("Remove command requires: taskId");
        }
        
        String taskIdStr = args.trim();
        
        try
        {
            Integer.parseInt(taskIdStr);
            return taskIdStr;
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Task ID must be a number");
        }
    }
    
    /**
     * Represents a parsed command with type and arguments.
     */
    public static class ParsedCommand
    {
        private final String command;
        private final String arguments;
        
        public ParsedCommand(String command, String arguments)
        {
            this.command = command;
            this.arguments = arguments;
        }
        
        public String getCommand()
        {
            return command;
        }
        
        public String getArguments()
        {
            return arguments;
        }
    }
}