package Solution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file operations for task persistence.
 * Uses plain text files as required by the assignment.
 */
public class FileHandler
{
    private static final String DEFAULT_FILENAME = "tasks.csv";
    
    /**
     * Saves tasks to a CSV file.
     * @param tasks List of tasks to save
     * @param filename Name of the file to save to
     * @throws IOException if file operation fails
     */
    public void saveTasksToFile(List<Task> tasks, String filename) throws IOException
    {
        if (filename == null || filename.trim().isEmpty())
        {
            filename = DEFAULT_FILENAME;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            for (Task task : tasks)
            {
                writer.write(task.toCSV());
                writer.newLine();
            }
        }
    }
    
    /**
     * Saves tasks to default CSV file.
     * @param tasks List of tasks to save
     * @throws IOException if file operation fails
     */
    public void saveTasksToFile(List<Task> tasks) throws IOException
    {
        saveTasksToFile(tasks, DEFAULT_FILENAME);
    }
    
    /**
     * Loads tasks from a CSV file.
     * @param filename Name of the file to load from
     * @return List of loaded tasks
     * @throws IOException if file operation fails
     * @throws IllegalArgumentException if file format is invalid
     */
    public List<Task> loadTasksFromFile(String filename) throws IOException
    {
        if (filename == null || filename.trim().isEmpty())
        {
            filename = DEFAULT_FILENAME;
        }
        
        List<Task> tasks = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null)
            {
                lineNumber++;
                
                if (line.trim().isEmpty())
                {
                    continue; // Skip empty lines
                }
                
                try
                {
                    Task task = Task.fromCSV(line);
                    tasks.add(task);
                }
                catch (IllegalArgumentException e)
                {
                    throw new IOException(
                        String.format("Error parsing line %d: %s", lineNumber, e.getMessage()));
                }
            }
        }
        
        return tasks;
    }
    
    /**
     * Loads tasks from default CSV file.
     * @return List of loaded tasks
     * @throws IOException if file operation fails
     */
    public List<Task> loadTasksFromFile() throws IOException
    {
        return loadTasksFromFile(DEFAULT_FILENAME);
    }
    
    /**
     * Checks if the data file exists.
     * @param filename File name to check
     * @return true if file exists, false otherwise
     */
    public boolean fileExists(String filename)
    {
        if (filename == null || filename.trim().isEmpty())
        {
            filename = DEFAULT_FILENAME;
        }
        
        java.io.File file = new java.io.File(filename);
        return file.exists() && file.isFile();
    }
}