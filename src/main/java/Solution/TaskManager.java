package Solution;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages collection of tasks with operations for CRUD functionality.
 * Handles task storage and retrieval with proper validation.
 */
public class TaskManager
{
    private final List<Task> tasks;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Initializes an empty task manager.
     */
    public TaskManager()
    {
        tasks = new ArrayList<>();
    }
    
    /**
     * Creates a new task with validation.
     * @param description Task description
     * @param priorityStr Priority as string
     * @param dueDateStr Due date as string (yyyy-MM-dd)
     * @return Created task
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Task createTask(String description, String priorityStr, String dueDateStr)
    {
        try
        {
            Task.Priority priority = Task.Priority.fromString(priorityStr);
            LocalDate dueDate = parseDate(dueDateStr);
            
            Task task = new Task(description, priority, dueDate);
            tasks.add(task);
            
            return task;
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Failed to create task: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves all tasks.
     * @return List of all tasks
     */
    public List<Task> getAllTasks()
    {
        return new ArrayList<>(tasks);
    }
    
    /**
     * Retrieves tasks filtered by status.
     * @param statusStr Status filter
     * @return List of filtered tasks
     * @throws IllegalArgumentException if status is invalid
     */
    public List<Task> getTasksByStatus(String statusStr)
    {
        Task.Status status = Task.Status.fromString(statusStr);
        
        return tasks.stream()
            .filter(task -> task.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    /**
     * Retrieves tasks sorted by due date.
     * @return List of tasks sorted by due date
     */
    public List<Task> getTasksSortedByDueDate()
    {
        return tasks.stream()
            .sorted(Comparator.comparing(Task::getDueDate))
            .collect(Collectors.toList());
    }
    
    /**
     * Finds a task by ID.
     * @param id Task ID
     * @return Task if found, null otherwise
     */
    public Task getTaskById(int id)
    {
        return tasks.stream()
            .filter(task -> task.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Updates task status.
     * @param taskId Task ID
     * @param statusStr New status
     * @return Updated task
     * @throws IllegalArgumentException if task not found or status invalid
     */
    public Task updateTaskStatus(int taskId, String statusStr)
    {
        Task task = getTaskById(taskId);
        
        if (task == null)
        {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
        
        Task.Status newStatus = Task.Status.fromString(statusStr);
        task.setStatus(newStatus);
        
        return task;
    }
    
    /**
     * Updates task description.
     * @param taskId Task ID
     * @param description New description
     * @return Updated task
     * @throws IllegalArgumentException if task not found or description invalid
     */
    public Task updateTaskDescription(int taskId, String description)
    {
        Task task = getTaskById(taskId);
        
        if (task == null)
        {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
        
        task.setDescription(description);
        return task;
    }
    
    /**
     * Updates task priority.
     * @param taskId Task ID
     * @param priorityStr New priority
     * @return Updated task
     * @throws IllegalArgumentException if task not found or priority invalid
     */
    public Task updateTaskPriority(int taskId, String priorityStr)
    {
        Task task = getTaskById(taskId);
        
        if (task == null)
        {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
        
        Task.Priority priority = Task.Priority.fromString(priorityStr);
        task.setPriority(priority);
        
        return task;
    }
    
    /**
     * Updates task due date.
     * @param taskId Task ID
     * @param dueDateStr New due date
     * @return Updated task
     * @throws IllegalArgumentException if task not found or date invalid
     */
    public Task updateTaskDueDate(int taskId, String dueDateStr)
    {
        Task task = getTaskById(taskId);
        
        if (task == null)
        {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
        
        LocalDate dueDate = parseDate(dueDateStr);
        task.setDueDate(dueDate);
        
        return task;
    }
    
    /**
     * Removes a task by ID.
     * @param taskId Task ID to remove
     * @return true if task was removed, false if not found
     */
    public boolean removeTask(int taskId)
    {
        Task task = getTaskById(taskId);
        
        if (task != null)
        {
            tasks.remove(task);
            return true;
        }
        
        return false;
    }
    
    /**
     * Parses date string with validation.
     * @param dateStr Date string in yyyy-MM-dd format
     * @return LocalDate object
     * @throws IllegalArgumentException if date format is invalid
     */
    private LocalDate parseDate(String dateStr)
    {
        if (dateStr == null || dateStr.trim().isEmpty())
        {
            throw new IllegalArgumentException("Date cannot be empty");
        }
        
        try
        {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        }
        catch (DateTimeParseException e)
        {
            throw new IllegalArgumentException(
                "Invalid date format. Please use yyyy-MM-dd format. Example: 2024-12-31");
        }
    }
    
    /**
     * Gets task count by status.
     * @return String with task counts
     */
    public String getTaskCounts()
    {
        long todoCount = tasks.stream().filter(t -> t.getStatus() == Task.Status.TODO).count();
        long doingCount = tasks.stream().filter(t -> t.getStatus() == Task.Status.DOING).count();
        long doneCount = tasks.stream().filter(t -> t.getStatus() == Task.Status.DONE).count();
        
        return String.format("To Do: %d | Doing: %d | Done: %d | Total: %d",
            todoCount, doingCount, doneCount, tasks.size());
    }
    
    /**
     * Clears all tasks (for testing purposes).
     */
    public void clearAllTasks()
    {
        tasks.clear();
        Task.resetIdCounter();
    }
}