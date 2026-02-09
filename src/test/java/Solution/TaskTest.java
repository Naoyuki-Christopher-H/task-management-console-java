package Solution;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;

/**
 * JUnit 4 test class for Task entity.
 * Tests validation, getters/setters, and serialization methods.
 */
public class TaskTest
{
    private Task task;
    private LocalDate tomorrow;
    
    /**
     * Setup method executed before each test.
     * Initializes test data.
     */
    @Before
    public void setUp()
    {
        tomorrow = LocalDate.now().plusDays(1);
        task = new Task("Test Task", Task.Priority.MEDIUM, tomorrow);
    }
    
    /**
     * Cleanup method executed after each test.
     */
    @After
    public void tearDown()
    {
        Task.resetIdCounter();
    }
    
    /**
     * Test task creation with valid parameters.
     */
    @Test
    public void testTaskCreationValid()
    {
        assertNotNull("Task should not be null", task);
        assertEquals("Description should match", "Test Task", task.getDescription());
        assertEquals("Priority should be MEDIUM", Task.Priority.MEDIUM, task.getPriority());
        assertEquals("Due date should be tomorrow", tomorrow, task.getDueDate());
        assertEquals("Default status should be TODO", Task.Status.TODO, task.getStatus());
        assertTrue("ID should be positive", task.getId() > 0);
    }
    
    /**
     * Test task creation with null description.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTaskCreationNullDescription()
    {
        new Task(null, Task.Priority.MEDIUM, tomorrow);
    }
    
    /**
     * Test task creation with empty description.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTaskCreationEmptyDescription()
    {
        new Task("", Task.Priority.MEDIUM, tomorrow);
    }
    
    /**
     * Test task creation with whitespace-only description.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTaskCreationWhitespaceDescription()
    {
        new Task("   ", Task.Priority.MEDIUM, tomorrow);
    }
    
    /**
     * Test task creation with null priority.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTaskCreationNullPriority()
    {
        new Task("Test Task", null, tomorrow);
    }
    
    /**
     * Test task creation with null due date.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTaskCreationNullDueDate()
    {
        new Task("Test Task", Task.Priority.MEDIUM, null);
    }
    
    /**
     * Test task creation with past due date.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTaskCreationPastDueDate()
    {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        new Task("Test Task", Task.Priority.MEDIUM, yesterday);
    }
    
    /**
     * Test task creation with today as due date (should be allowed).
     */
    @Test
    public void testTaskCreationTodayDueDate()
    {
        LocalDate today = LocalDate.now();
        Task todayTask = new Task("Today Task", Task.Priority.LOW, today);
        assertEquals("Due date should be today", today, todayTask.getDueDate());
    }
    
    /**
     * Test setDescription with valid value.
     */
    @Test
    public void testSetDescriptionValid()
    {
        task.setDescription("Updated Description");
        assertEquals("Description should be updated", "Updated Description", task.getDescription());
    }
    
    /**
     * Test setDescription with null value.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDescriptionNull()
    {
        task.setDescription(null);
    }
    
    /**
     * Test setDescription with empty value.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDescriptionEmpty()
    {
        task.setDescription("");
    }
    
    /**
     * Test setPriority with valid values.
     */
    @Test
    public void testSetPriorityValid()
    {
        task.setPriority(Task.Priority.LOW);
        assertEquals("Priority should be LOW", Task.Priority.LOW, task.getPriority());
        
        task.setPriority(Task.Priority.HIGH);
        assertEquals("Priority should be HIGH", Task.Priority.HIGH, task.getPriority());
    }
    
    /**
     * Test setPriority with null value.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetPriorityNull()
    {
        task.setPriority(null);
    }
    
    /**
     * Test setDueDate with valid future date.
     */
    @Test
    public void testSetDueDateValid()
    {
        LocalDate newDate = LocalDate.now().plusDays(10);
        task.setDueDate(newDate);
        assertEquals("Due date should be updated", newDate, task.getDueDate());
    }
    
    /**
     * Test setDueDate with past date.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDueDatePast()
    {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        task.setDueDate(pastDate);
    }
    
    /**
     * Test setDueDate with null value.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDueDateNull()
    {
        task.setDueDate(null);
    }
    
    /**
     * Test setDueDate with today (should be allowed).
     */
    @Test
    public void testSetDueDateToday()
    {
        LocalDate today = LocalDate.now();
        task.setDueDate(today);
        assertEquals("Due date should be today", today, task.getDueDate());
    }
    
    /**
     * Test setStatus with valid values.
     */
    @Test
    public void testSetStatusValid()
    {
        task.setStatus(Task.Status.DOING);
        assertEquals("Status should be DOING", Task.Status.DOING, task.getStatus());
        
        task.setStatus(Task.Status.DONE);
        assertEquals("Status should be DONE", Task.Status.DONE, task.getStatus());
        
        task.setStatus(Task.Status.TODO);
        assertEquals("Status should be TODO", Task.Status.TODO, task.getStatus());
    }
    
    /**
     * Test setStatus with null value.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetStatusNull()
    {
        task.setStatus(null);
    }
    
    /**
     * Test Priority.fromString with valid strings.
     */
    @Test
    public void testPriorityFromStringValid()
    {
        assertEquals("LOW from string", Task.Priority.LOW, Task.Priority.fromString("LOW"));
        assertEquals("MEDIUM from string", Task.Priority.MEDIUM, Task.Priority.fromString("MEDIUM"));
        assertEquals("HIGH from string", Task.Priority.HIGH, Task.Priority.fromString("HIGH"));
        assertEquals("Case insensitive", Task.Priority.LOW, Task.Priority.fromString("low"));
        assertEquals("Mixed case", Task.Priority.MEDIUM, Task.Priority.fromString("Medium"));
    }
    
    /**
     * Test Priority.fromString with invalid string.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPriorityFromStringInvalid()
    {
        Task.Priority.fromString("INVALID");
    }
    
    /**
     * Test Priority.fromString with null string.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPriorityFromStringNull()
    {
        Task.Priority.fromString(null);
    }
    
    /**
     * Test Status.fromString with valid strings.
     */
    @Test
    public void testStatusFromStringValid()
    {
        assertEquals("TODO from 'To Do'", Task.Status.TODO, Task.Status.fromString("To Do"));
        assertEquals("TODO from 'TODO'", Task.Status.TODO, Task.Status.fromString("TODO"));
        assertEquals("DOING from 'Doing'", Task.Status.DOING, Task.Status.fromString("Doing"));
        assertEquals("DOING from 'DOING'", Task.Status.DOING, Task.Status.fromString("DOING"));
        assertEquals("DONE from 'Done'", Task.Status.DONE, Task.Status.fromString("Done"));
        assertEquals("DONE from 'DONE'", Task.Status.DONE, Task.Status.fromString("DONE"));
        assertEquals("Case insensitive", Task.Status.TODO, Task.Status.fromString("to do"));
    }
    
    /**
     * Test Status.fromString with invalid string.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStatusFromStringInvalid()
    {
        Task.Status.fromString("INVALID");
    }
    
    /**
     * Test Status.fromString with null string.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStatusFromStringNull()
    {
        Task.Status.fromString(null);
    }
    
    /**
     * Test toString method format.
     */
    @Test
    public void testToStringFormat()
    {
        String result = task.toString();
        assertTrue("Should contain ID", result.contains("ID: " + task.getId()));
        assertTrue("Should contain description", result.contains("Description: Test Task"));
        assertTrue("Should contain priority", result.contains("Priority: MEDIUM"));
        assertTrue("Should contain due date", result.contains("Due: " + tomorrow));
        assertTrue("Should contain status", result.contains("Status: To Do"));
    }
    
    /**
     * Test toString with long description (should be truncated).
     */
    @Test
    public void testToStringLongDescription()
    {
        String longDescription = "This is a very long task description that should be truncated in the display";
        Task longTask = new Task(longDescription, Task.Priority.HIGH, tomorrow);
        String result = longTask.toString();
        
        // Description should be truncated to 30 chars with "..."
        assertTrue("Should contain truncated description", result.contains("..."));
        assertTrue("Should contain 'Description:' label", result.contains("Description:"));
    }
    
    /**
     * Test toCSV method format.
     */
    @Test
    public void testToCSVFormat()
    {
        String csv = task.toCSV();
        String[] parts = csv.split(",");
        
        assertEquals("CSV should have 5 parts", 5, parts.length);
        assertEquals("First part should be ID", String.valueOf(task.getId()), parts[0]);
        assertEquals("Second part should be description", "Test Task", parts[1]);
        assertEquals("Third part should be priority", "MEDIUM", parts[2]);
        assertEquals("Fourth part should be due date", tomorrow.toString(), parts[3]);
        assertEquals("Fifth part should be status", "TODO", parts[4]);
    }
    
    /**
     * Test toCSV with description containing commas (should be escaped).
     */
    @Test
    public void testToCSVWithCommaInDescription()
    {
        Task commaTask = new Task("Task, with, commas", Task.Priority.LOW, tomorrow);
        String csv = commaTask.toCSV();
        
        // Commas in description should be replaced with semicolons
        assertFalse("CSV should not contain commas in description", csv.contains("Task, with, commas"));
        assertTrue("Commas should be replaced with semicolons", csv.contains("Task; with; commas"));
    }
    
    /**
     * Test fromCSV with valid CSV string.
     */
    @Test
    public void testFromCSVValid()
    {
        String csv = "1,Test Task,MEDIUM,2024-12-31,TODO";
        Task parsedTask = Task.fromCSV(csv);
        
        assertNotNull("Parsed task should not be null", parsedTask);
        assertEquals("ID should match", 1, parsedTask.getId());
        assertEquals("Description should match", "Test Task", parsedTask.getDescription());
        assertEquals("Priority should be MEDIUM", Task.Priority.MEDIUM, parsedTask.getPriority());
        assertEquals("Due date should match", LocalDate.parse("2024-12-31"), parsedTask.getDueDate());
        assertEquals("Status should be TODO", Task.Status.TODO, parsedTask.getStatus());
    }
    
    /**
     * Test fromCSV with description containing escaped commas.
     */
    @Test
    public void testFromCSVWithEscapedCommas()
    {
        String csv = "2,Task; with; semicolons,LOW,2024-12-31,DOING";
        Task parsedTask = Task.fromCSV(csv);
        
        assertEquals("Semicolons should be converted to commas", 
                     "Task, with, semicolons", parsedTask.getDescription());
    }
    
    /**
     * Test fromCSV with invalid CSV format (too few parts).
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromCSVInvalidFormat()
    {
        Task.fromCSV("1,Test Task,MEDIUM");
    }
    
    /**
     * Test fromCSV with null CSV string.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromCSVNull()
    {
        Task.fromCSV(null);
    }
    
    /**
     * Test fromCSV with empty CSV string.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromCSVEmpty()
    {
        Task.fromCSV("");
    }
    
    /**
     * Test fromCSV with invalid date format.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromCSVInvalidDate()
    {
        Task.fromCSV("1,Test Task,MEDIUM,invalid-date,TODO");
    }
    
    /**
     * Test fromCSV with invalid priority.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromCSVInvalidPriority()
    {
        Task.fromCSV("1,Test Task,INVALID,2024-12-31,TODO");
    }
    
    /**
     * Test fromCSV with invalid status.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromCSVInvalidStatus()
    {
        Task.fromCSV("1,Test Task,MEDIUM,2024-12-31,INVALID");
    }
    
    /**
     * Test ID counter reset.
     */
    @Test
    public void testIdCounterReset()
    {
        // Create first task
        Task task1 = new Task("Task 1", Task.Priority.LOW, tomorrow);
        assertEquals("First task ID should be 1", 1, task1.getId());
        
        // Reset counter
        Task.resetIdCounter();
        
        // Create second task after reset
        Task task2 = new Task("Task 2", Task.Priority.MEDIUM, tomorrow);
        assertEquals("Task after reset should have ID 1", 1, task2.getId());
    }
    
    /**
     * Test ID auto-increment.
     */
    @Test
    public void testIdAutoIncrement()
    {
        Task.resetIdCounter();
        
        Task task1 = new Task("Task 1", Task.Priority.LOW, tomorrow);
        Task task2 = new Task("Task 2", Task.Priority.MEDIUM, tomorrow);
        Task task3 = new Task("Task 3", Task.Priority.HIGH, tomorrow);
        
        assertEquals("First task ID should be 1", 1, task1.getId());
        assertEquals("Second task ID should be 2", 2, task2.getId());
        assertEquals("Third task ID should be 3", 3, task3.getId());
    }
    
    /**
     * Test Status.toString method returns display name.
     */
    @Test
    public void testStatusToString()
    {
        assertEquals("TODO toString should return 'To Do'", "To Do", Task.Status.TODO.toString());
        assertEquals("DOING toString should return 'Doing'", "Doing", Task.Status.DOING.toString());
        assertEquals("DONE toString should return 'Done'", "Done", Task.Status.DONE.toString());
    }
}