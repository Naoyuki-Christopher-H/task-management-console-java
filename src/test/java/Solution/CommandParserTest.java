package Solution;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import Solution.CommandParser.ParsedCommand;

/**
 * JUnit 4 test class for CommandParser.
 * Tests command parsing and validation logic.
 */
public class CommandParserTest
{
    private CommandParser commandParser;
    
    /**
     * Setup method executed before each test.
     */
    @Before
    public void setUp()
    {
        commandParser = new CommandParser();
    }
    
    /**
     * Test parse with simple command.
     */
    @Test
    public void testParseSimpleCommand()
    {
        ParsedCommand result = commandParser.parse("list");
        
        assertNotNull("Parsed command should not be null", result);
        assertEquals("Command should be 'list'", "list", result.getCommand());
        assertEquals("Arguments should be empty", "", result.getArguments());
    }
    
    /**
     * Test parse with command and arguments.
     */
    @Test
    public void testParseCommandWithArguments()
    {
        ParsedCommand result = commandParser.parse("create \"Task 1\", HIGH, 2024-12-31");
        
        assertNotNull("Parsed command should not be null", result);
        assertEquals("Command should be 'create'", "create", result.getCommand());
        assertEquals("Arguments should match", "\"Task 1\", HIGH, 2024-12-31", result.getArguments());
    }
    
    /**
     * Test parse with multiple spaces.
     */
    @Test
    public void testParseWithMultipleSpaces()
    {
        ParsedCommand result = commandParser.parse("  update   status   1   Doing  ");
        
        assertNotNull("Parsed command should not be null", result);
        assertEquals("Command should be 'update'", "update", result.getCommand());
        assertEquals("Arguments should be trimmed", "status   1   Doing", result.getArguments());
    }
    
    /**
     * Test parse with uppercase command.
     */
    @Test
    public void testParseUppercaseCommand()
    {
        ParsedCommand result = commandParser.parse("CREATE \"Task\", MEDIUM, 2024-12-31");
        
        assertEquals("Command should be lowercase", "create", result.getCommand());
        assertEquals("Arguments should preserve case", "\"Task\", MEDIUM, 2024-12-31", result.getArguments());
    }
    
    /**
     * Test parse with mixed case command.
     */
    @Test
    public void testParseMixedCaseCommand()
    {
        ParsedCommand result = commandParser.parse("UpDaTe status 1 Doing");
        
        assertEquals("Command should be lowercase", "update", result.getCommand());
    }
    
    /**
     * Test parse with empty input.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyInput()
    {
        commandParser.parse("");
    }
    
    /**
     * Test parse with whitespace-only input.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseWhitespaceOnly()
    {
        commandParser.parse("   ");
    }
    
    /**
     * Test parse with null input.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseNullInput()
    {
        commandParser.parse(null);
    }
    
    /**
     * Test validateCreateArgs with valid arguments.
     */
    @Test
    public void testValidateCreateArgsValid()
    {
        String args = "\"Complete project\", HIGH, 2024-12-31";
        String[] result = commandParser.validateCreateArgs(args);
        
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 3 parts", 3, result.length);
        assertEquals("Description should match", "Complete project", result[0]);
        assertEquals("Priority should be HIGH", "HIGH", result[1]);
        assertEquals("Due date should match", "2024-12-31", result[2]);
    }
    
    /**
     * Test validateCreateArgs with description without quotes.
     */
    @Test
    public void testValidateCreateArgsWithoutQuotes()
    {
        String args = "SimpleTask, LOW, 2024-12-31";
        String[] result = commandParser.validateCreateArgs(args);
        
        assertEquals("Description should match", "SimpleTask", result[0]);
        assertEquals("Priority should be LOW", "LOW", result[1]);
        assertEquals("Due date should match", "2024-12-31", result[2]);
    }
    
    /**
     * Test validateCreateArgs with description containing commas inside quotes.
     */
    @Test
    public void testValidateCreateArgsWithCommasInDescription()
    {
        String args = "\"Task, with, commas\", MEDIUM, 2024-12-31";
        String[] result = commandParser.validateCreateArgs(args);
        
        assertEquals("Description should preserve commas", "Task, with, commas", result[0]);
        assertEquals("Priority should be MEDIUM", "MEDIUM", result[1]);
        assertEquals("Due date should match", "2024-12-31", result[2]);
    }
    
    /**
     * Test validateCreateArgs with insufficient arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateCreateArgsInsufficient()
    {
        commandParser.validateCreateArgs("\"Task only\"");
    }
    
    /**
     * Test validateCreateArgs with empty arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateCreateArgsEmpty()
    {
        commandParser.validateCreateArgs("");
    }
    
    /**
     * Test validateCreateArgs with null arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateCreateArgsNull()
    {
        commandParser.validateCreateArgs(null);
    }
    
    /**
     * Test validateCreateArgs with empty description.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateCreateArgsEmptyDescription()
    {
        commandParser.validateCreateArgs("\"\", HIGH, 2024-12-31");
    }
    
    /**
     * Test validateCreateArgs with invalid priority.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateCreateArgsInvalidPriority()
    {
        commandParser.validateCreateArgs("\"Task\", INVALID, 2024-12-31");
    }
    
    /**
     * Test validateUpdateStatusArgs with valid arguments.
     */
    @Test
    public void testValidateUpdateStatusArgsValid()
    {
        String args = "1 Doing";
        String[] result = commandParser.validateUpdateStatusArgs(args);
        
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 2 parts", 2, result.length);
        assertEquals("Task ID should match", "1", result[0]);
        assertEquals("Status should match", "Doing", result[1]);
    }
    
    /**
     * Test validateUpdateStatusArgs with quoted status.
     */
    @Test
    public void testValidateUpdateStatusArgsQuotedStatus()
    {
        String args = "1 \"To Do\"";
        String[] result = commandParser.validateUpdateStatusArgs(args);
        
        assertEquals("Task ID should match", "1", result[0]);
        assertEquals("Status should match", "\"To Do\"", result[1]);
    }
    
    /**
     * Test validateUpdateStatusArgs with insufficient arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateUpdateStatusArgsInsufficient()
    {
        commandParser.validateUpdateStatusArgs("1");
    }
    
    /**
     * Test validateUpdateStatusArgs with empty arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateUpdateStatusArgsEmpty()
    {
        commandParser.validateUpdateStatusArgs("");
    }
    
    /**
     * Test validateUpdateStatusArgs with null arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateUpdateStatusArgsNull()
    {
        commandParser.validateUpdateStatusArgs(null);
    }
    
    /**
     * Test validateUpdateStatusArgs with non-numeric task ID.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateUpdateStatusArgsNonNumericId()
    {
        commandParser.validateUpdateStatusArgs("abc Doing");
    }
    
    /**
     * Test validateUpdateStatusArgs with invalid status.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateUpdateStatusArgsInvalidStatus()
    {
        commandParser.validateUpdateStatusArgs("1 INVALID");
    }
    
    /**
     * Test validateRemoveArgs with valid arguments.
     */
    @Test
    public void testValidateRemoveArgsValid()
    {
        String args = "123";
        String result = commandParser.validateRemoveArgs(args);
        
        assertEquals("Task ID should match", "123", result);
    }
    
    /**
     * Test validateRemoveArgs with extra spaces.
     */
    @Test
    public void testValidateRemoveArgsWithSpaces()
    {
        String args = "   456   ";
        String result = commandParser.validateRemoveArgs(args);
        
        assertEquals("Task ID should be trimmed", "456", result);
    }
    
    /**
     * Test validateRemoveArgs with empty arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateRemoveArgsEmpty()
    {
        commandParser.validateRemoveArgs("");
    }
    
    /**
     * Test validateRemoveArgs with null arguments.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateRemoveArgsNull()
    {
        commandParser.validateRemoveArgs(null);
    }
    
    /**
     * Test validateRemoveArgs with non-numeric ID.
     * Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateRemoveArgsNonNumeric()
    {
        commandParser.validateRemoveArgs("abc");
    }
    
    /**
     * Test validateRemoveArgs with negative number.
     */
    @Test
    public void testValidateRemoveArgsNegativeNumber()
    {
        String result = commandParser.validateRemoveArgs("-1");
        assertEquals("Negative numbers should be accepted as strings", "-1", result);
    }
    
    /**
     * Test ParsedCommand constructor and getters.
     */
    @Test
    public void testParsedCommandGetters()
    {
        ParsedCommand pc = new ParsedCommand("test", "arguments here");
        
        assertEquals("Command should match", "test", pc.getCommand());
        assertEquals("Arguments should match", "arguments here", pc.getArguments());
    }
    
    /**
     * Test ParsedCommand with empty command.
     */
    @Test
    public void testParsedCommandEmptyCommand()
    {
        ParsedCommand pc = new ParsedCommand("", "args");
        
        assertEquals("Empty command should be allowed", "", pc.getCommand());
        assertEquals("Arguments should match", "args", pc.getArguments());
    }
    
    /**
     * Test ParsedCommand with null arguments.
     */
    @Test
    public void testParsedCommandNullArguments()
    {
        ParsedCommand pc = new ParsedCommand("cmd", null);
        
        assertEquals("Command should match", "cmd", pc.getCommand());
        assertNull("Arguments should be null", pc.getArguments());
    }
    
    /**
     * Test complex command parsing.
     */
    @Test
    public void testComplexCommandParsing()
    {
        String input = "update description 123 \"New description with spaces\"";
        ParsedCommand result = commandParser.parse(input);
        
        assertEquals("Command should be 'update'", "update", result.getCommand());
        assertEquals("Arguments should match", "description 123 \"New description with spaces\"", result.getArguments());
    }
    
    /**
     * Test command with only command and no arguments.
     */
    @Test
    public void testCommandWithoutArguments()
    {
        ParsedCommand result = commandParser.parse("help");
        
        assertEquals("Command should be 'help'", "help", result.getCommand());
        assertEquals("Arguments should be empty", "", result.getArguments());
    }
    
    /**
     * Test command with trailing spaces.
     */
    @Test
    public void testCommandWithTrailingSpaces()
    {
        ParsedCommand result = commandParser.parse("list   ");
        
        assertEquals("Command should be 'list'", "list", result.getCommand());
        assertEquals("Arguments should be empty", "", result.getArguments());
    }
    
    /**
     * Test command with leading and trailing spaces.
     */
    @Test
    public void testCommandWithLeadingTrailingSpaces()
    {
        ParsedCommand result = commandParser.parse("   clear   ");
        
        assertEquals("Command should be 'clear'", "clear", result.getCommand());
        assertEquals("Arguments should be empty", "", result.getArguments());
    }
    
    /**
     * Test command with tab characters.
     */
    @Test
    public void testCommandWithTabs()
    {
        ParsedCommand result = commandParser.parse("create\t\"Task\"\tHIGH\t2024-12-31");
        
        assertEquals("Command should be 'create'", "create", result.getCommand());
        assertEquals("Arguments should include tabs", "\"Task\"\tHIGH\t2024-12-31", result.getArguments());
    }
}