package Solution;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;

/**
 * JUnit 4 test class for TaskManagementConsoleJava.
 * Tests user interaction and command processing.
 */
public class TaskManagementConsoleJavaTest
{
    private TaskManagementConsoleJava app;
    private final ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private InputStream originalIn;
    
    /**
     * Constructor initializes output stream capture.
     */
    public TaskManagementConsoleJavaTest()
    {
        outputStream = new ByteArrayOutputStream();
    }
    
    /**
     * Setup method executed before each test.
     */
    @Before
    public void setUp()
    {
        // Save original System.out and System.in
        originalOut = System.out;
        originalIn = System.in;
        
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));
        
        // Reset Task ID counter
        Task.resetIdCounter();
    }
    
    /**
     * Cleanup method executed after each test.
     */
    @After
    public void tearDown()
    {
        // Restore original System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
        
        // Clear output stream
        outputStream.reset();
    }
    
    /**
     * Helper method to provide input to System.in.
     */
    private void provideInput(String input)
    {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
    
    /**
     * Helper method to get captured output.
     */
    private String getOutput()
    {
        return outputStream.toString();
    }
    
    /**
     * Helper method to simulate application run with input.
     */
    private void runAppWithInput(String input)
    {
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        // Run in a separate thread to avoid blocking
        Thread appThread = new Thread(() -> 
        {
            try
            {
                app.run();
            }
            catch (Exception e)
            {
                // Expected for testing
            }
        });
        
        appThread.start();
        
        try
        {
            // Wait for app to process input
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Test application displays welcome message.
     */
    @Test
    public void testWelcomeMessage()
    {
        provideInput("exit\n");
        app = new TaskManagementConsoleJava();
        
        // Run app in current thread for this test
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display welcome message", 
                   output.contains("TASK MANAGEMENT CONSOLE APPLICATION"));
        assertTrue("Should display version", output.contains("V-1.0.0"));
        assertTrue("Should prompt for commands", output.contains("Type 'help'"));
    }
    
    /**
     * Test help command output.
     */
    @Test
    public void testHelpCommand()
    {
        provideInput("help\nexit\n");
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display help header", output.contains("Available Commands"));
        assertTrue("Should include CREATE command", output.contains("CREATE/ADD"));
        assertTrue("Should include LIST command", output.contains("LIST/SHOW"));
        assertTrue("Should include UPDATE command", output.contains("UPDATE/CHANGE"));
        assertTrue("Should include REMOVE command", output.contains("REMOVE/DELETE"));
        assertTrue("Should include CLEAR command", output.contains("CLEAR/CLS"));
        assertTrue("Should include HELP command", output.contains("HELP"));
        assertTrue("Should include EXIT command", output.contains("EXIT/QUIT"));
    }
    
    /**
     * Test create command with valid input.
     */
    @Test
    public void testCreateCommandValid()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Test Task\", HIGH, " + tomorrow + "\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm task creation", output.contains("Task created successfully"));
        assertTrue("Should display task details", output.contains("Test Task"));
        assertTrue("Should display priority", output.contains("HIGH"));
        assertTrue("Should display due date", output.contains(tomorrow));
        assertTrue("Should display default status", output.contains("To Do"));
    }
    
    /**
     * Test create command with invalid date format.
     */
    @Test
    public void testCreateCommandInvalidDateFormat()
    {
        String input = "create \"Test Task\", HIGH, 2024/12/31\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display error message", output.contains("Error:"));
        assertTrue("Should mention date format", output.contains("date format"));
        assertTrue("Should provide example", output.contains("yyyy-MM-dd"));
    }
    
    /**
     * Test create command with past date.
     */
    @Test
    public void testCreateCommandPastDate()
    {
        String input = "create \"Test Task\", HIGH, 2020-01-01\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display error for past date", output.contains("cannot be earlier"));
        assertTrue("Should mention current date", output.contains("current date"));
    }
    
    /**
     * Test create command with invalid priority.
     */
    @Test
    public void testCreateCommandInvalidPriority()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Test Task\", INVALID, " + tomorrow + "\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display error for invalid priority", output.contains("Invalid priority"));
        assertTrue("Should list valid priorities", output.contains("LOW, MEDIUM, HIGH"));
    }
    
    /**
     * Test list command with no tasks.
     */
    @Test
    public void testListCommandNoTasks()
    {
        String input = "list\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should indicate no tasks found", output.contains("No tasks found"));
    }
    
    /**
     * Test list command with tasks.
     */
    @Test
    public void testListCommandWithTasks()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Task 1\", LOW, " + tomorrow + "\n" +
                      "create \"Task 2\", MEDIUM, " + tomorrow + "\n" +
                      "list\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display task header", output.contains("=== Tasks ==="));
        assertTrue("Should display task counts", output.contains("To Do:"));
        assertTrue("Should display Task 1", output.contains("Task 1"));
        assertTrue("Should display Task 2", output.contains("Task 2"));
        assertTrue("Should display LOW priority", output.contains("LOW"));
        assertTrue("Should display MEDIUM priority", output.contains("MEDIUM"));
    }
    
    /**
     * Test list command filtered by status.
     */
    @Test
    public void testListCommandFiltered()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Task 1\", LOW, " + tomorrow + "\n" +
                      "create \"Task 2\", MEDIUM, " + tomorrow + "\n" +
                      "update status 1 Doing\n" +
                      "list Doing\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display Doing tasks only", output.contains("Task 1"));
        assertFalse("Should not display Task 2 (still To Do)", 
                    output.contains("Task 2") && getOutput().indexOf("Task 2") > getOutput().indexOf("list Doing"));
    }
    
    /**
     * Test update status command.
     */
    @Test
    public void testUpdateStatusCommand()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Test Task\", HIGH, " + tomorrow + "\n" +
                      "update status 1 Doing\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm status update", output.contains("Task status updated"));
        assertTrue("Should show Doing status", output.contains("Doing"));
    }
    
    /**
     * Test update status with invalid task ID.
     */
    @Test
    public void testUpdateStatusInvalidTaskId()
    {
        String input = "update status 999 Doing\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should display task not found error", output.contains("not found"));
        assertTrue("Should mention task ID", output.contains("999"));
    }
    
    /**
     * Test update description command.
     */
    @Test
    public void testUpdateDescriptionCommand()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Old Description\", MEDIUM, " + tomorrow + "\n" +
                      "update desc 1 \"New Description\"\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm description update", output.contains("Task description updated"));
        assertTrue("Should show new description", output.contains("New Description"));
    }
    
    /**
     * Test update priority command.
     */
    @Test
    public void testUpdatePriorityCommand()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Test Task\", LOW, " + tomorrow + "\n" +
                      "update priority 1 HIGH\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm priority update", output.contains("Task priority updated"));
        assertTrue("Should show HIGH priority", output.contains("HIGH"));
    }
    
    /**
     * Test update due date command.
     */
    @Test
    public void testUpdateDueDateCommand()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String nextWeek = LocalDate.now().plusDays(7).toString();
        String input = "create \"Test Task\", MEDIUM, " + tomorrow + "\n" +
                      "update due 1 " + nextWeek + "\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm due date update", output.contains("Task due date updated"));
        assertTrue("Should show new due date", output.contains(nextWeek));
    }
    
    /**
     * Test remove command.
     */
    @Test
    public void testRemoveCommand()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Task to remove\", LOW, " + tomorrow + "\n" +
                      "remove 1\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm removal", output.contains("removed successfully"));
    }
    
    /**
     * Test remove command with invalid task ID.
     */
    @Test
    public void testRemoveCommandInvalidId()
    {
        String input = "remove 999\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should indicate task not found", output.contains("not found"));
    }
    
    /**
     * Test clear command.
     */
    @Test
    public void testClearCommand()
    {
        String input = "clear\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should confirm console cleared", output.contains("Console cleared"));
        assertTrue("Should mention data preserved", output.contains("preserved in memory"));
    }
    
    /**
     * Test exit command.
     */
    @Test
    public void testExitCommand()
    {
        String input = "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should save tasks to file", output.contains("Tasks saved to file"));
        assertTrue("Should display goodbye message", output.contains("Goodbye"));
    }
    
    /**
     * Test quit command alias.
     */
    @Test
    public void testQuitCommand()
    {
        String input = "quit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should exit application", output.contains("Goodbye"));
    }
    
    /**
     * Test unknown command handling.
     */
    @Test
    public void testUnknownCommand()
    {
        String input = "unknowncommand\nexit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should indicate unknown command", output.contains("Unknown command"));
        assertTrue("Should suggest help", output.contains("Type 'help'"));
    }
    
    /**
     * Test command case insensitivity.
     */
    @Test
    public void testCommandCaseInsensitivity()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "CREATE \"Test Task\", HIGH, " + tomorrow + "\n" +
                      "LIST\n" +
                      "EXIT\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should accept uppercase CREATE", output.contains("Task created successfully"));
        assertTrue("Should accept uppercase LIST", output.contains("=== Tasks ==="));
    }
    
    /**
     * Test command aliases.
     */
    @Test
    public void testCommandAliases()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "add \"Task 1\", LOW, " + tomorrow + "\n" +
                      "show\n" +
                      "delete 1\n" +
                      "cls\n" +
                      "quit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should accept 'add' alias", output.contains("Task created successfully"));
        assertTrue("Should accept 'show' alias", output.contains("=== Tasks ==="));
        assertTrue("Should accept 'delete' alias", output.contains("removed successfully"));
        assertTrue("Should accept 'cls' alias", output.contains("Console cleared"));
        assertTrue("Should accept 'quit' alias", output.contains("Goodbye"));
    }
    
    /**
     * Test error recovery after invalid command.
     */
    @Test
    public void testErrorRecovery()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Valid Task\", HIGH, " + tomorrow + "\n" +
                      "invalid command with error\n" +
                      "list\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        assertTrue("Should create task successfully", output.contains("Task created successfully"));
        assertTrue("Should handle invalid command", output.contains("Unknown command"));
        assertTrue("Should continue with list command", output.contains("=== Tasks ==="));
    }
    
    /**
     * Test multiple commands in sequence.
     */
    @Test
    public void testMultipleCommandsSequence()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String nextWeek = LocalDate.now().plusDays(7).toString();
        
        String input = "create \"Initial Task\", MEDIUM, " + tomorrow + "\n" +
                      "update status 1 Doing\n" +
                      "update priority 1 HIGH\n" +
                      "update due 1 " + nextWeek + "\n" +
                      "list\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        // Verify all operations completed
        assertTrue("Should create task", output.contains("Task created successfully"));
        assertTrue("Should update status", output.contains("Task status updated"));
        assertTrue("Should update priority", output.contains("Task priority updated"));
        assertTrue("Should update due date", output.contains("Task due date updated"));
        assertTrue("Should list tasks", output.contains("=== Tasks ==="));
        
        // Verify final state in list output
        int listIndex = output.lastIndexOf("=== Tasks ===");
        String listOutput = output.substring(listIndex);
        assertTrue("List should show Doing status", listOutput.contains("Doing"));
        assertTrue("List should show HIGH priority", listOutput.contains("HIGH"));
        assertTrue("List should show updated due date", listOutput.contains(nextWeek));
    }
    
    /**
     * Test that application prompts for next command after each operation.
     */
    @Test
    public void testCommandPromptContinuity()
    {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String input = "create \"Task 1\", LOW, " + tomorrow + "\n" +
                      "create \"Task 2\", MEDIUM, " + tomorrow + "\n" +
                      "exit\n";
        
        provideInput(input);
        app = new TaskManagementConsoleJava();
        
        try
        {
            app.run();
        }
        catch (Exception e)
        {
            // Ignore for test
        }
        
        String output = getOutput();
        // Count command prompts ("> ")
        long promptCount = output.chars()
            .filter(ch -> output.indexOf("> ", (int) ch) != -1)
            .count();
        
        assertTrue("Should prompt after each command", promptCount >= 3);
    }
}