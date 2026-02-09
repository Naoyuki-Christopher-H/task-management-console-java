package Solution;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit 4 test class for FileHandler.
 * Tests file operations and CSV persistence.
 */
public class FileHandlerTest
{
    private FileHandler fileHandler;
    private String testFileName;
    private List<Task> testTasks;
    
    /**
     * Setup method executed before each test.
     */
    @Before
    public void setUp()
    {
        fileHandler = new FileHandler();
        testFileName = "test_tasks.csv";
        testTasks = createTestTasks();
        Task.resetIdCounter();
        
        // Clean up any existing test file
        deleteTestFile();
    }
    
    /**
     * Cleanup method executed after each test.
     */
    @After
    public void tearDown()
    {
        deleteTestFile();
    }
    
    /**
     * Helper method to delete test file.
     */
    private void deleteTestFile()
    {
        try
        {
            Files.deleteIfExists(Paths.get(testFileName));
        }
        catch (IOException e)
        {
            // Ignore cleanup errors
        }
    }
    
    /**
     * Helper method to create test tasks.
     */
    private List<Task> createTestTasks()
    {
        List<Task> tasks = new ArrayList<>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        
        tasks.add(new Task("Task 1", Task.Priority.LOW, tomorrow));
        tasks.add(new Task("Task 2", Task.Priority.MEDIUM, tomorrow.plusDays(1)));
        tasks.add(new Task("Task, with comma", Task.Priority.HIGH, tomorrow.plusDays(2)));
        
        // Set different statuses
        tasks.get(0).setStatus(Task.Status.TODO);
        tasks.get(1).setStatus(Task.Status.DOING);
        tasks.get(2).setStatus(Task.Status.DONE);
        
        return tasks;
    }
    
    /**
     * Test saveTasksToFile with valid tasks and filename.
     */
    @Test
    public void testSaveTasksToFileWithFilename() throws IOException
    {
        fileHandler.saveTasksToFile(testTasks, testFileName);
        
        File file = new File(testFileName);
        assertTrue("File should exist", file.exists());
        assertTrue("File should not be empty", file.length() > 0);
        
        // Verify file content
        List<String> lines = Files.readAllLines(Paths.get(testFileName));
        assertEquals("Should have 3 lines", 3, lines.size());
        
        // Verify each line
        for (int i = 0; i < testTasks.size(); i++)
        {
            Task task = testTasks.get(i);
            String expectedLine = task.toCSV();
            assertEquals("Line " + i + " should match", expectedLine, lines.get(i));
        }
    }
    
    /**
     * Test saveTasksToFile with null filename (should use default).
     */
    @Test
    public void testSaveTasksToFileNullFilename() throws IOException
    {
        // This should create the default file
        fileHandler.saveTasksToFile(testTasks, null);
        
        File defaultFile = new File("tasks.csv");
        assertTrue("Default file should exist", defaultFile.exists());
        
        // Clean up default file
        Files.deleteIfExists(Paths.get("tasks.csv"));
    }
    
    /**
     * Test saveTasksToFile with empty filename (should use default).
     */
    @Test
    public void testSaveTasksToFileEmptyFilename() throws IOException
    {
        fileHandler.saveTasksToFile(testTasks, "");
        
        File defaultFile = new File("tasks.csv");
        assertTrue("Default file should exist", defaultFile.exists());
        
        // Clean up default file
        Files.deleteIfExists(Paths.get("tasks.csv"));
    }
    
    /**
     * Test saveTasksToFile with empty task list.
     */
    @Test
    public void testSaveTasksToFileEmptyList() throws IOException
    {
        List<Task> emptyList = new ArrayList<>();
        fileHandler.saveTasksToFile(emptyList, testFileName);
        
        File file = new File(testFileName);
        assertTrue("File should exist", file.exists());
        assertEquals("File should be empty", 0, file.length());
    }
    
    /**
     * Test saveTasksToFile with write permission denied.
     * Should throw IOException.
     */
    @Test(expected = IOException.class)
    public void testSaveTasksToFilePermissionDenied() throws IOException
    {
        if (!System.getProperty("os.name").toLowerCase().contains("windows"))
        {
            // Create a read-only directory (Unix/Linux)
            Path readOnlyDir = Files.createTempDirectory("readonly");
            readOnlyDir.toFile().setReadOnly();
            
            String protectedFile = readOnlyDir.resolve("test.csv").toString();
            fileHandler.saveTasksToFile(testTasks, protectedFile);
        }
        else
        {
            // On Windows, skip test or use different approach
            throw new IOException("Skipping permission test on Windows");
        }
    }
    
    /**
     * Test saveTasksToFile overwrites existing file.
     */
    @Test
    public void testSaveTasksToFileOverwritesExisting() throws IOException
    {
        // Create initial file with different content
        List<Task> initialTasks = new ArrayList<>();
        initialTasks.add(new Task("Old Task", Task.Priority.LOW, LocalDate.now().plusDays(1)));
        
        fileHandler.saveTasksToFile(initialTasks, testFileName);
        long initialSize = new File(testFileName).length();
        
        // Save new tasks (should overwrite)
        fileHandler.saveTasksToFile(testTasks, testFileName);
        long newSize = new File(testFileName).length();
        
        assertNotEquals("File size should change", initialSize, newSize);
        
        // Verify new content
        List<String> lines = Files.readAllLines(Paths.get(testFileName));
        assertEquals("Should have new task count", testTasks.size(), lines.size());
    }
    
    /**
     * Test loadTasksFromFile with valid file.
     */
    @Test
    public void testLoadTasksFromFileValid() throws IOException
    {
        // First save test tasks
        fileHandler.saveTasksToFile(testTasks, testFileName);
        
        // Then load them
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        
        assertNotNull("Loaded tasks should not be null", loadedTasks);
        assertEquals("Should load same number of tasks", testTasks.size(), loadedTasks.size());
        
        // Verify each task matches
        for (int i = 0; i < testTasks.size(); i++)
        {
            Task expected = testTasks.get(i);
            Task actual = loadedTasks.get(i);
            
            assertEquals("Task " + i + " ID should match", expected.getId(), actual.getId());
            assertEquals("Task " + i + " description should match", expected.getDescription(), actual.getDescription());
            assertEquals("Task " + i + " priority should match", expected.getPriority(), actual.getPriority());
            assertEquals("Task " + i + " due date should match", expected.getDueDate(), actual.getDueDate());
            assertEquals("Task " + i + " status should match", expected.getStatus(), actual.getStatus());
        }
    }
    
    /**
     * Test loadTasksFromFile with null filename (should use default).
     */
    @Test
    public void testLoadTasksFromFileNullFilename() throws IOException
    {
        // Save to default file
        fileHandler.saveTasksToFile(testTasks, "tasks.csv");
        
        // Load with null filename
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(null);
        
        assertNotNull("Loaded tasks should not be null", loadedTasks);
        assertEquals("Should load tasks from default file", testTasks.size(), loadedTasks.size());
        
        // Clean up
        Files.deleteIfExists(Paths.get("tasks.csv"));
    }
    
    /**
     * Test loadTasksFromFile with empty filename (should use default).
     */
    @Test
    public void testLoadTasksFromFileEmptyFilename() throws IOException
    {
        // Save to default file
        fileHandler.saveTasksToFile(testTasks, "tasks.csv");
        
        // Load with empty filename
        List<Task> loadedTasks = fileHandler.loadTasksFromFile("");
        
        assertNotNull("Loaded tasks should not be null", loadedTasks);
        assertEquals("Should load tasks from default file", testTasks.size(), loadedTasks.size());
        
        // Clean up
        Files.deleteIfExists(Paths.get("tasks.csv"));
    }
    
    /**
     * Test loadTasksFromFile with non-existent file.
     * Should throw IOException.
     */
    @Test(expected = IOException.class)
    public void testLoadTasksFromFileNonExistent() throws IOException
    {
        fileHandler.loadTasksFromFile("non_existent_file.csv");
    }
    
    /**
     * Test loadTasksFromFile with empty file.
     */
    @Test
    public void testLoadTasksFromFileEmpty() throws IOException
    {
        // Create empty file
        Files.createFile(Paths.get(testFileName));
        
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        
        assertNotNull("Loaded tasks should not be null", loadedTasks);
        assertTrue("Loaded tasks should be empty", loadedTasks.isEmpty());
    }
    
    /**
     * Test loadTasksFromFile with file containing blank lines.
     */
    @Test
    public void testLoadTasksFromFileWithBlankLines() throws IOException
    {
        // Create file with blank lines
        List<String> lines = new ArrayList<>();
        lines.add(testTasks.get(0).toCSV());
        lines.add("");  // Blank line
        lines.add(testTasks.get(1).toCSV());
        lines.add("   ");  // Whitespace line
        lines.add(testTasks.get(2).toCSV());
        
        Files.write(Paths.get(testFileName), lines);
        
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        
        assertEquals("Should load only non-blank lines", 3, loadedTasks.size());
    }
    
    /**
     * Test loadTasksFromFile with invalid CSV format.
     * Should throw IOException.
     */
    @Test(expected = IOException.class)
    public void testLoadTasksFromFileInvalidFormat() throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add("1,Task 1");  // Missing fields
        lines.add("invalid,format,here");  // Wrong number of fields
        
        Files.write(Paths.get(testFileName), lines);
        
        fileHandler.loadTasksFromFile(testFileName);
    }
    
    /**
     * Test loadTasksFromFile with invalid date in CSV.
     * Should throw IOException.
     */
    @Test(expected = IOException.class)
    public void testLoadTasksFromFileInvalidDate() throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add("1,Task 1,LOW,invalid-date,TODO");
        
        Files.write(Paths.get(testFileName), lines);
        
        fileHandler.loadTasksFromFile(testFileName);
    }
    
    /**
     * Test loadTasksFromFile with invalid priority in CSV.
     * Should throw IOException.
     */
    @Test(expected = IOException.class)
    public void testLoadTasksFromFileInvalidPriority() throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add("1,Task 1,INVALID,2024-12-31,TODO");
        
        Files.write(Paths.get(testFileName), lines);
        
        fileHandler.loadTasksFromFile(testFileName);
    }
    
    /**
     * Test loadTasksFromFile with invalid status in CSV.
     * Should throw IOException.
     */
    @Test(expected = IOException.class)
    public void testLoadTasksFromFileInvalidStatus() throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add("1,Task 1,LOW,2024-12-31,INVALID");
        
        Files.write(Paths.get(testFileName), lines);
        
        fileHandler.loadTasksFromFile(testFileName);
    }
    
    /**
     * Test loadTasksFromFile preserves ID counter.
     */
    @Test
    public void testLoadTasksFromFilePreservesIdCounter() throws IOException
    {
        // Create tasks with specific IDs
        Task.resetIdCounter();
        Task task1 = new Task("Task 1", Task.Priority.LOW, LocalDate.now().plusDays(1));
        Task task2 = new Task("Task 2", Task.Priority.MEDIUM, LocalDate.now().plusDays(2));
        
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        
        // Save and reset
        fileHandler.saveTasksToFile(tasks, testFileName);
        Task.resetIdCounter();
        
        // Load tasks
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        
        // Create new task after loading - should have correct ID
        Task newTask = new Task("New Task", Task.Priority.HIGH, LocalDate.now().plusDays(3));
        
        // ID should be max(loaded IDs) + 1 = 3
        assertEquals("New task ID should be 3", 3, newTask.getId());
    }
    
    /**
     * Test fileExists with existing file.
     */
    @Test
    public void testFileExistsWithExistingFile() throws IOException
    {
        Files.createFile(Paths.get(testFileName));
        
        boolean exists = fileHandler.fileExists(testFileName);
        assertTrue("File should exist", exists);
    }
    
    /**
     * Test fileExists with non-existent file.
     */
    @Test
    public void testFileExistsWithNonExistentFile()
    {
        boolean exists = fileHandler.fileExists("non_existent_file.csv");
        assertFalse("File should not exist", exists);
    }
    
    /**
     * Test fileExists with null filename (should check default).
     */
    @Test
    public void testFileExistsNullFilename() throws IOException
    {
        // Create default file
        Files.createFile(Paths.get("tasks.csv"));
        
        boolean exists = fileHandler.fileExists(null);
        assertTrue("Should check default file", exists);
        
        // Clean up
        Files.deleteIfExists(Paths.get("tasks.csv"));
    }
    
    /**
     * Test fileExists with empty filename (should check default).
     */
    @Test
    public void testFileExistsEmptyFilename() throws IOException
    {
        // Create default file
        Files.createFile(Paths.get("tasks.csv"));
        
        boolean exists = fileHandler.fileExists("");
        assertTrue("Should check default file", exists);
        
        // Clean up
        Files.deleteIfExists(Paths.get("tasks.csv"));
    }
    
    /**
     * Test fileExists with directory instead of file.
     */
    @Test
    public void testFileExistsWithDirectory()
    {
        boolean exists = fileHandler.fileExists(".");
        assertFalse("Directory should not be considered a file", exists);
    }
    
    /**
     * Test round-trip save and load.
     */
    @Test
    public void testRoundTripSaveAndLoad() throws IOException
    {
        // Save tasks
        fileHandler.saveTasksToFile(testTasks, testFileName);
        
        // Load tasks
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        
        // Save loaded tasks to new file
        String newFileName = "test_tasks_roundtrip.csv";
        fileHandler.saveTasksToFile(loadedTasks, newFileName);
        
        // Compare files
        byte[] originalBytes = Files.readAllBytes(Paths.get(testFileName));
        byte[] roundTripBytes = Files.readAllBytes(Paths.get(newFileName));
        
        assertArrayEquals("Files should be identical after round-trip", originalBytes, roundTripBytes);
        
        // Clean up new file
        Files.deleteIfExists(Paths.get(newFileName));
    }
    
    /**
     * Test CSV escaping of commas in description.
     */
    @Test
    public void testCSVEscaping() throws IOException
    {
        Task taskWithComma = new Task("Task, with, commas", Task.Priority.HIGH, LocalDate.now().plusDays(1));
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskWithComma);
        
        fileHandler.saveTasksToFile(tasks, testFileName);
        
        // Load and verify
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        assertEquals("Should load 1 task", 1, loadedTasks.size());
        
        Task loadedTask = loadedTasks.get(0);
        assertEquals("Description with commas should be preserved", 
                     "Task, with, commas", loadedTask.getDescription());
    }
    
    /**
     * Test loading from file with trailing newline.
     */
    @Test
    public void testLoadWithTrailingNewline() throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add(testTasks.get(0).toCSV());
        lines.add(testTasks.get(1).toCSV());
        lines.add("");  // Extra newline at end
        
        Files.write(Paths.get(testFileName), lines);
        
        List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFileName);
        
        assertEquals("Should ignore trailing empty line", 2, loadedTasks.size());
    }
    
    /**
     * Test error message on invalid CSV line.
     */
    @Test
    public void testErrorMessageOnInvalidCSV() throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add("1,Valid Task,LOW,2024-12-31,TODO");
        lines.add("invalid,line,format");  // Invalid line
        
        Files.write(Paths.get(testFileName), lines);
        
        try
        {
            fileHandler.loadTasksFromFile(testFileName);
            fail("Should have thrown IOException");
        }
        catch (IOException e)
        {
            assertTrue("Error message should contain line number", 
                       e.getMessage().contains("line 2") || e.getMessage().contains("Error parsing line"));
        }
    }
}