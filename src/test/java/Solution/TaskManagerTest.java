package Solution;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

/**
 * JUnit 4 test class for TaskManager business logic.
 * Tests task management operations and business rules.
 */
public class TaskManagerTest
{
    private TaskManager taskManager;
    private LocalDate tomorrow;
    
    /**
     * Setup method executed before each test.
     */
    @Before
    public void setUp()
    {
        taskManager = new TaskManager();
        tomorrow = LocalDate.now().plusDays(1);
        Task.resetIdCounter();
    }
    
    /**
     * Cleanup method executed after each test.
     */
    @After
    public void tearDown()
    {
        taskManager.clearAllTasks();
    }
    
    /**
     * Test createTask with valid parameters.
     */
    @Test
    public void testCreateTaskValid()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        
        assertNotNull("Created task should not be null", task);
        assertEquals("Task description should match", "Test Task", task.getDescription());
        assertEquals("Task priority should be MEDIUM", Task.Priority.MEDIUM, task.getPriority());
        assertEquals("Task due date should match", tomorrow, task.getDueDate());
        assertEquals("Task status should be TODO", Task.Status.TODO, task.getStatus());
        
        List<Task> allTasks = taskManager.getAllTasks();
        assertEquals("Task list should contain 1 task", 1, allTasks.size());
        assertTrue("Task list should contain created task", allTasks.contains(task));
    }
    
    /**
     * Test createTask with invalid priority.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTaskInvalidPriority()
    {
        taskManager.createTask("Test Task", "INVALID", tomorrow.toString());
    }
    
    /**
     * Test createTask with invalid date format.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTaskInvalidDateFormat()
    {
        taskManager.createTask("Test Task", "MEDIUM", "2024/12/31");
    }
    
    /**
     * Test createTask with past date.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTaskPastDate()
    {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        taskManager.createTask("Test Task", "MEDIUM", yesterday.toString());
    }
    
    /**
     * Test getAllTasks with empty manager.
     */
    @Test
    public void testGetAllTasksEmpty()
    {
        List<Task> tasks = taskManager.getAllTasks();
        assertNotNull("Task list should not be null", tasks);
        assertTrue("Task list should be empty", tasks.isEmpty());
    }
    
    /**
     * Test getAllTasks with multiple tasks.
     */
    @Test
    public void testGetAllTasksMultiple()
    {
        Task task1 = taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        Task task2 = taskManager.createTask("Task 2", "MEDIUM", tomorrow.plusDays(1).toString());
        Task task3 = taskManager.createTask("Task 3", "HIGH", tomorrow.plusDays(2).toString());
        
        List<Task> tasks = taskManager.getAllTasks();
        assertEquals("Should have 3 tasks", 3, tasks.size());
        assertTrue("Should contain task1", tasks.contains(task1));
        assertTrue("Should contain task2", tasks.contains(task2));
        assertTrue("Should contain task3", tasks.contains(task3));
    }
    
    /**
     * Test getTasksByStatus with TODO status.
     */
    @Test
    public void testGetTasksByStatusTODO()
    {
        Task task1 = taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        Task task2 = taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        
        // Update one task to DOING
        taskManager.updateTaskStatus(task2.getId(), "Doing");
        
        List<Task> todoTasks = taskManager.getTasksByStatus("To Do");
        assertEquals("Should have 1 TODO task", 1, todoTasks.size());
        assertTrue("Should contain task1", todoTasks.contains(task1));
        assertFalse("Should not contain task2", todoTasks.contains(task2));
    }
    
    /**
     * Test getTasksByStatus with DOING status.
     */
    @Test
    public void testGetTasksByStatusDOING()
    {
        Task task1 = taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        Task task2 = taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        
        taskManager.updateTaskStatus(task1.getId(), "Doing");
        taskManager.updateTaskStatus(task2.getId(), "Doing");
        
        List<Task> doingTasks = taskManager.getTasksByStatus("Doing");
        assertEquals("Should have 2 DOING tasks", 2, doingTasks.size());
        assertTrue("Should contain task1", doingTasks.contains(task1));
        assertTrue("Should contain task2", doingTasks.contains(task2));
    }
    
    /**
     * Test getTasksByStatus with invalid status.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetTasksByStatusInvalid()
    {
        taskManager.getTasksByStatus("INVALID");
    }
    
    /**
     * Test getTasksSortedByDueDate.
     */
    @Test
    public void testGetTasksSortedByDueDate()
    {
        LocalDate date1 = tomorrow;
        LocalDate date2 = tomorrow.plusDays(5);
        LocalDate date3 = tomorrow.plusDays(10);
        
        // Create tasks in reverse order
        Task task3 = taskManager.createTask("Task 3", "LOW", date3.toString());
        Task task1 = taskManager.createTask("Task 1", "MEDIUM", date1.toString());
        Task task2 = taskManager.createTask("Task 2", "HIGH", date2.toString());
        
        List<Task> sortedTasks = taskManager.getTasksSortedByDueDate();
        
        assertEquals("Should have 3 tasks", 3, sortedTasks.size());
        assertEquals("First task should be earliest due date", task1, sortedTasks.get(0));
        assertEquals("Second task should be middle due date", task2, sortedTasks.get(1));
        assertEquals("Third task should be latest due date", task3, sortedTasks.get(2));
    }
    
    /**
     * Test getTaskById with existing task.
     */
    @Test
    public void testGetTaskByIdExisting()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        Task foundTask = taskManager.getTaskById(task.getId());
        
        assertNotNull("Found task should not be null", foundTask);
        assertEquals("Found task should match created task", task, foundTask);
    }
    
    /**
     * Test getTaskById with non-existent task.
     */
    @Test
    public void testGetTaskByIdNonExistent()
    {
        Task foundTask = taskManager.getTaskById(999);
        assertNull("Should return null for non-existent task", foundTask);
    }
    
    /**
     * Test updateTaskStatus with valid parameters.
     */
    @Test
    public void testUpdateTaskStatusValid()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        
        Task updatedTask = taskManager.updateTaskStatus(task.getId(), "Doing");
        assertNotNull("Updated task should not be null", updatedTask);
        assertEquals("Status should be DOING", Task.Status.DOING, updatedTask.getStatus());
        
        updatedTask = taskManager.updateTaskStatus(task.getId(), "Done");
        assertEquals("Status should be DONE", Task.Status.DONE, updatedTask.getStatus());
        
        updatedTask = taskManager.updateTaskStatus(task.getId(), "To Do");
        assertEquals("Status should be TODO", Task.Status.TODO, updatedTask.getStatus());
    }
    
    /**
     * Test updateTaskStatus with non-existent task ID.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskStatusNonExistent()
    {
        taskManager.updateTaskStatus(999, "Doing");
    }
    
    /**
     * Test updateTaskStatus with invalid status.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskStatusInvalid()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        taskManager.updateTaskStatus(task.getId(), "INVALID");
    }
    
    /**
     * Test updateTaskDescription with valid parameters.
     */
    @Test
    public void testUpdateTaskDescriptionValid()
    {
        Task task = taskManager.createTask("Original Description", "MEDIUM", tomorrow.toString());
        
        Task updatedTask = taskManager.updateTaskDescription(task.getId(), "Updated Description");
        assertNotNull("Updated task should not be null", updatedTask);
        assertEquals("Description should be updated", "Updated Description", updatedTask.getDescription());
    }
    
    /**
     * Test updateTaskDescription with non-existent task ID.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskDescriptionNonExistent()
    {
        taskManager.updateTaskDescription(999, "New Description");
    }
    
    /**
     * Test updateTaskDescription with empty description.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskDescriptionEmpty()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        taskManager.updateTaskDescription(task.getId(), "");
    }
    
    /**
     * Test updateTaskPriority with valid parameters.
     */
    @Test
    public void testUpdateTaskPriorityValid()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        
        Task updatedTask = taskManager.updateTaskPriority(task.getId(), "HIGH");
        assertNotNull("Updated task should not be null", updatedTask);
        assertEquals("Priority should be HIGH", Task.Priority.HIGH, updatedTask.getPriority());
        
        updatedTask = taskManager.updateTaskPriority(task.getId(), "LOW");
        assertEquals("Priority should be LOW", Task.Priority.LOW, updatedTask.getPriority());
    }
    
    /**
     * Test updateTaskPriority with non-existent task ID.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskPriorityNonExistent()
    {
        taskManager.updateTaskPriority(999, "HIGH");
    }
    
    /**
     * Test updateTaskPriority with invalid priority.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskPriorityInvalid()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        taskManager.updateTaskPriority(task.getId(), "INVALID");
    }
    
    /**
     * Test updateTaskDueDate with valid parameters.
     */
    @Test
    public void testUpdateTaskDueDateValid()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        LocalDate newDueDate = tomorrow.plusDays(10);
        
        Task updatedTask = taskManager.updateTaskDueDate(task.getId(), newDueDate.toString());
        assertNotNull("Updated task should not be null", updatedTask);
        assertEquals("Due date should be updated", newDueDate, updatedTask.getDueDate());
    }
    
    /**
     * Test updateTaskDueDate with non-existent task ID.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskDueDateNonExistent()
    {
        taskManager.updateTaskDueDate(999, tomorrow.toString());
    }
    
    /**
     * Test updateTaskDueDate with past date.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskDueDatePast()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        LocalDate pastDate = LocalDate.now().minusDays(1);
        taskManager.updateTaskDueDate(task.getId(), pastDate.toString());
    }
    
    /**
     * Test updateTaskDueDate with invalid date format.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTaskDueDateInvalidFormat()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        taskManager.updateTaskDueDate(task.getId(), "invalid-date");
    }
    
    /**
     * Test removeTask with existing task.
     */
    @Test
    public void testRemoveTaskExisting()
    {
        Task task = taskManager.createTask("Test Task", "MEDIUM", tomorrow.toString());
        
        boolean removed = taskManager.removeTask(task.getId());
        assertTrue("Task should be removed", removed);
        
        List<Task> tasks = taskManager.getAllTasks();
        assertTrue("Task list should be empty", tasks.isEmpty());
        
        Task foundTask = taskManager.getTaskById(task.getId());
        assertNull("Task should not be found after removal", foundTask);
    }
    
    /**
     * Test removeTask with non-existent task.
     */
    @Test
    public void testRemoveTaskNonExistent()
    {
        boolean removed = taskManager.removeTask(999);
        assertFalse("Should return false for non-existent task", removed);
    }
    
    /**
     * Test removeTask multiple times.
     */
    @Test
    public void testRemoveTaskMultiple()
    {
        Task task1 = taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        Task task2 = taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        Task task3 = taskManager.createTask("Task 3", "HIGH", tomorrow.toString());
        
        // Remove task2
        assertTrue("Task2 should be removed", taskManager.removeTask(task2.getId()));
        assertEquals("Should have 2 tasks remaining", 2, taskManager.getAllTasks().size());
        
        // Remove task1
        assertTrue("Task1 should be removed", taskManager.removeTask(task1.getId()));
        assertEquals("Should have 1 task remaining", 1, taskManager.getAllTasks().size());
        
        // Remove task3
        assertTrue("Task3 should be removed", taskManager.removeTask(task3.getId()));
        assertTrue("Task list should be empty", taskManager.getAllTasks().isEmpty());
    }
    
    /**
     * Test getTaskCounts with empty manager.
     */
    @Test
    public void testGetTaskCountsEmpty()
    {
        String counts = taskManager.getTaskCounts();
        assertEquals("Counts for empty manager", "To Do: 0 | Doing: 0 | Done: 0 | Total: 0", counts);
    }
    
    /**
     * Test getTaskCounts with mixed status tasks.
     */
    @Test
    public void testGetTaskCountsMixed()
    {
        Task task1 = taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        Task task2 = taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        Task task3 = taskManager.createTask("Task 3", "HIGH", tomorrow.toString());
        Task task4 = taskManager.createTask("Task 4", "LOW", tomorrow.toString());
        
        // Update statuses
        taskManager.updateTaskStatus(task1.getId(), "Doing");  // Task1 -> DOING
        taskManager.updateTaskStatus(task2.getId(), "Done");   // Task2 -> DONE
        taskManager.updateTaskStatus(task3.getId(), "Done");   // Task3 -> DONE
        // Task4 remains TODO
        
        String counts = taskManager.getTaskCounts();
        assertEquals("Counts for mixed status", "To Do: 1 | Doing: 1 | Done: 2 | Total: 4", counts);
    }
    
    /**
     * Test clearAllTasks.
     */
    @Test
    public void testClearAllTasks()
    {
        taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        taskManager.createTask("Task 3", "HIGH", tomorrow.toString());
        
        assertEquals("Should have 3 tasks before clear", 3, taskManager.getAllTasks().size());
        
        taskManager.clearAllTasks();
        
        assertTrue("Task list should be empty after clear", taskManager.getAllTasks().isEmpty());
    }
    
    /**
     * Test clearAllTasks resets ID counter.
     */
    @Test
    public void testClearAllTasksResetsIdCounter()
    {
        taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        
        taskManager.clearAllTasks();
        
        // After clear, new task should have ID 1
        Task newTask = taskManager.createTask("New Task", "HIGH", tomorrow.toString());
        assertEquals("New task after clear should have ID 1", 1, newTask.getId());
    }
    
    /**
     * Test task operations maintain data integrity.
     */
    @Test
    public void testTaskDataIntegrity()
    {
        // Create task
        Task task = taskManager.createTask("Original Task", "MEDIUM", tomorrow.toString());
        int taskId = task.getId();
        
        // Update all fields
        taskManager.updateTaskDescription(taskId, "Updated Task");
        taskManager.updateTaskPriority(taskId, "HIGH");
        taskManager.updateTaskStatus(taskId, "Doing");
        LocalDate newDueDate = tomorrow.plusDays(5);
        taskManager.updateTaskDueDate(taskId, newDueDate.toString());
        
        // Retrieve and verify
        Task retrievedTask = taskManager.getTaskById(taskId);
        assertNotNull("Task should still exist", retrievedTask);
        assertEquals("Description should be updated", "Updated Task", retrievedTask.getDescription());
        assertEquals("Priority should be HIGH", Task.Priority.HIGH, retrievedTask.getPriority());
        assertEquals("Status should be DOING", Task.Status.DOING, retrievedTask.getStatus());
        assertEquals("Due date should be updated", newDueDate, retrievedTask.getDueDate());
    }
    
    /**
     * Test concurrent task creation maintains unique IDs.
     */
    @Test
    public void testConcurrentTaskCreationIds()
    {
        Task task1 = taskManager.createTask("Task 1", "LOW", tomorrow.toString());
        Task task2 = taskManager.createTask("Task 2", "MEDIUM", tomorrow.toString());
        Task task3 = taskManager.createTask("Task 3", "HIGH", tomorrow.toString());
        
        assertNotEquals("Task IDs should be unique", task1.getId(), task2.getId());
        assertNotEquals("Task IDs should be unique", task1.getId(), task3.getId());
        assertNotEquals("Task IDs should be unique", task2.getId(), task3.getId());
    }
}