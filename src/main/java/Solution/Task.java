package Solution;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with unique identifier, description, priority, due date, and status.
 * Follows object-oriented design principles with proper encapsulation.
 */
public class Task
{
    // Static counter for generating unique IDs
    private static int idCounter = 1;
    
    // Task attributes with private access for encapsulation
    private final int id;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private Status status;
    
    /**
     * Enumeration for task priority levels.
     * Ensures only valid priority values are used.
     */
    public enum Priority
    {
        LOW,
        MEDIUM,
        HIGH;
        
        /**
         * Converts string to Priority enum with validation.
         * @param priorityStr String representation of priority
         * @return Priority enum value
         * @throws IllegalArgumentException if string is not a valid priority
         */
        public static Priority fromString(String priorityStr)
        {
            if (priorityStr == null)
            {
                throw new IllegalArgumentException("Priority cannot be null");
            }
            
            try
            {
                return Priority.valueOf(priorityStr.toUpperCase());
            }
            catch (IllegalArgumentException e)
            {
                throw new IllegalArgumentException(
                    "Invalid priority. Valid values are: LOW, MEDIUM, HIGH");
            }
        }
    }
    
    /**
     * Enumeration for task statuses.
     * Ensures only valid status values are used as per requirements.
     */
    public enum Status
    {
        TODO("To Do"),
        DOING("Doing"),
        DONE("Done");
        
        private final String displayName;
        
        Status(String displayName)
        {
            this.displayName = displayName;
        }
        
        /**
         * Converts string to Status enum with validation.
         * @param statusStr String representation of status
         * @return Status enum value
         * @throws IllegalArgumentException if string is not a valid status
         */
        public static Status fromString(String statusStr)
        {
            if (statusStr == null)
            {
                throw new IllegalArgumentException("Status cannot be null");
            }
            
            String normalized = statusStr.toUpperCase().replace(" ", "");
            
            for (Status status : Status.values())
            {
                if (status.name().equals(normalized) || 
                    status.displayName.equalsIgnoreCase(statusStr))
                {
                    return status;
                }
            }
            
            throw new IllegalArgumentException(
                "Invalid status. Valid values are: To Do, Doing, Done");
        }
        
        @Override
        public String toString()
        {
            return displayName;
        }
    }
    
    /**
     * Constructor for creating a new task.
     * @param description Task description
     * @param priority Task priority
     * @param dueDate Task due date
     * @throws IllegalArgumentException if due date is in the past
     */
    public Task(String description, Priority priority, LocalDate dueDate)
    {
        if (description == null || description.trim().isEmpty())
        {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        if (priority == null)
        {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        
        if (dueDate == null)
        {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        
        // Validate that due date is not earlier than current date
        LocalDate currentDate = LocalDate.now();
        if (dueDate.isBefore(currentDate))
        {
            throw new IllegalArgumentException(
                "Due date cannot be earlier than current date: " + currentDate);
        }
        
        this.id = idCounter++;
        this.description = description.trim();
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = Status.TODO; // Default status as per requirements
    }
    
    // Getter methods for task attributes
    public int getId()
    {
        return id;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public Priority getPriority()
    {
        return priority;
    }
    
    public LocalDate getDueDate()
    {
        return dueDate;
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    /**
     * Updates the task description.
     * @param description New description
     * @throws IllegalArgumentException if description is null or empty
     */
    public void setDescription(String description)
    {
        if (description == null || description.trim().isEmpty())
        {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description.trim();
    }
    
    /**
     * Updates the task priority.
     * @param priority New priority
     * @throws IllegalArgumentException if priority is null
     */
    public void setPriority(Priority priority)
    {
        if (priority == null)
        {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }
    
    /**
     * Updates the task due date with validation.
     * @param dueDate New due date
     * @throws IllegalArgumentException if due date is null or in the past
     */
    public void setDueDate(LocalDate dueDate)
    {
        if (dueDate == null)
        {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        
        LocalDate currentDate = LocalDate.now();
        if (dueDate.isBefore(currentDate))
        {
            throw new IllegalArgumentException(
                "Due date cannot be earlier than current date: " + currentDate);
        }
        
        this.dueDate = dueDate;
    }
    
    /**
     * Updates the task status.
     * @param status New status
     * @throws IllegalArgumentException if status is null
     */
    public void setStatus(Status status)
    {
        if (status == null)
        {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }
    
    /**
     * Resets the ID counter (useful for testing).
     */
    public static void resetIdCounter()
    {
        idCounter = 1;
    }
    
    /**
     * Returns formatted string representation of the task.
     * @return Formatted task information
     */
    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format(
            "ID: %d | Description: %-30s | Priority: %-6s | Due: %s | Status: %s",
            id,
            description.length() > 30 ? description.substring(0, 27) + "..." : description,
            priority,
            dueDate.format(formatter),
            status);
    }
    
    /**
     * Returns CSV representation for file storage.
     * @return CSV string
     */
    public String toCSV()
    {
        return String.format(
            "%d,%s,%s,%s,%s",
            id,
            description.replace(",", ";"), // Escape commas in description
            priority,
            dueDate,
            status.name());
    }
    
    /**
     * Creates a Task from CSV string.
     * @param csv CSV string
     * @return Task object
     * @throws IllegalArgumentException if CSV format is invalid
     */
    public static Task fromCSV(String csv)
    {
        if (csv == null || csv.trim().isEmpty())
        {
            throw new IllegalArgumentException("CSV string cannot be empty");
        }
        
        String[] parts = csv.split(",", 5);
        
        if (parts.length != 5)
        {
            throw new IllegalArgumentException("Invalid CSV format: " + csv);
        }
        
        try
        {
            int id = Integer.parseInt(parts[0]);
            String description = parts[1].replace(";", ",");
            Priority priority = Priority.valueOf(parts[2]);
            LocalDate dueDate = LocalDate.parse(parts[3]);
            Status status = Status.valueOf(parts[4]);
            
            Task task = new Task(description, priority, dueDate);
            task.status = status;
            
            // Update ID counter to avoid conflicts
            if (id >= idCounter)
            {
                idCounter = id + 1;
            }
            
            return task;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Failed to parse CSV: " + e.getMessage());
        }
    }
}