# Task Management Console Application - Complete Documentation

## Table of Contents
- [Project Overview](#project-overview)
  - [Purpose and Scope](#purpose-and-scope)
  - [Key Features](#key-features)
- [System Architecture](#system-architecture)
  - [Component Overview](#component-overview)
  - [Class Relationships](#class-relationships)
- [Installation and Setup](#installation-and-setup)
  - [Prerequisites](#prerequisites)
  - [Building from Source](#building-from-source)
  - [Running the Application](#running-the-application)
- [Configuration](#configuration)
  - [File Structure](#file-structure)
- [User Guide](#user-guide)
  - [Getting Started](#getting-started)
  - [Basic Operations](#basic-operations)
- [Command Reference](#command-reference)
  - [Task Management Commands](#task-management-commands)
  - [Viewing Commands](#viewing-commands)
  - [Utility Commands](#utility-commands)
- [Data Model Specification](#data-model-specification)
  - [Task Entity Definition](#task-entity-definition)
  - [Status Management System](#status-management-system)
  - [Priority System](#priority-system)
- [Data Persistence](#data-persistence)
  - [File Storage System](#file-storage-system)
  - [CSV Format Specification](#csv-format-specification)
- [Error Handling System](#error-handling-system)
  - [Error Categories](#error-categories)
  - [Error Messages](#error-messages)
- [Testing Framework](#testing-framework)
  - [Unit Testing Strategy](#unit-testing-strategy)
  - [Test Organization](#test-organization)
  - [Running Tests](#running-tests)
  - [Test Coverage](#test-coverage)
- [Development Guide](#development-guide)
  - [Code Organization](#code-organization)
  - [Build System](#build-system)
  - [Coding Standards](#coding-standards)
- [Performance Considerations](#performance-considerations)
  - [Memory Management](#memory-management)
- [Security Considerations](#security-considerations)
  - [Input Validation](#input-validation)
- [Troubleshooting Guide](#troubleshooting-guide)
  - [Common Issues](#common-issues)
- [Limitations and Constraints](#limitations-and-constraints)
  - [Technical Limitations](#technical-limitations)
- [Appendix](#appendix)
  - [Quick Reference Guide](#quick-reference-guide)

## Project Overview

### Purpose and Scope

The Task Management Console Application is a Java-based command-line utility 
designed for efficient task organization and management. This application 
provides a console interface for creating, tracking, and managing tasks with 
comprehensive validation and persistence capabilities.

The application is built with Java 8 and follows object-oriented design 
principles. It operates entirely in the console without requiring graphical 
interfaces or external databases. Task data is stored in memory during execution 
and persisted to plain CSV files.

### Key Features

1. **Complete Task Management**: Create, read, update, and delete tasks
2. **Advanced Validation**: Comprehensive input validation for dates, priorities, and statuses
3. **Automatic Persistence**: CSV-based file storage with automatic loading and saving
4. **Cross-Platform Compatibility**: Works on Windows, Linux, and macOS
5. **Console Clearing**: Built-in command to clear console screen while preserving data
6. **Comprehensive Testing**: Complete JUnit 4 test suite with 247 unit tests
7. **Robust Error Handling**: Informative error messages with recovery guidance

## System Architecture

### Component Overview

The application follows a modular architecture with five main components:

1. **TaskManagementConsoleJava**: Main application controller handling user interaction
2. **Task**: Data model representing task entities with validation logic
3. **TaskManager**: Business logic component managing task operations
4. **CommandParser**: Input parsing and validation component
5. **FileHandler**: File I/O operations for data persistence

### Class Relationships

```
TaskManagementConsoleJava (Main)
         │
         ├── TaskManager (Business Logic)
         │       │
         │       └── Task (Data Model)
         │
         ├── CommandParser (Input Handling)
         │
         └── FileHandler (Persistence)
```

## Installation and Setup

### Prerequisites

1. **Java Development Kit (JDK) 8 or higher**
   - Verify installation: `java -version`
   - Download from Oracle or OpenJDK

2. **Apache Maven 3.6.0 or higher** (for building from source)
   - Verify installation: `mvn -version`
   - Required for dependency management and building

3. **Command Line Interface**
   - Windows: Command Prompt or PowerShell
   - Linux/macOS: Terminal or shell
   - Minimum terminal size: 80 columns for proper display

### Building from Source

#### Step 1: Clone Repository
```bash
git clone https://github.com/Naoyuki-Christopher-H/task-management-console-java.git
cd task-management-console-java
```

#### Step 2: Build with Maven
```bash
# Clean previous builds
mvn clean

# Compile the project
mvn compile

# Run tests
mvn test

# Create executable JAR
mvn package
```

#### Step 3: Verify Build
After successful build, verify the executable JAR exists:
- `target/task-management-console-java-V-1.0.0.jar`

### Running the Application

#### Option 1: Run with Maven
```bash
mvn exec:java -Dexec.mainClass="Solution.TaskManagementConsoleJava"
```

#### Option 2: Run from Executable JAR
```bash
java -jar target/task-management-console-java-V-1.0.0.jar
```

#### Option 3: Direct Java Execution
```bash
cd target
java -jar task-management-console-java-V-1.0.0.jar
```

## Configuration

### File Structure

#### Application Directory Layout
```
task-management-console-java/
├── src/main/java/Solution/          # Source code
│   ├── TaskManagementConsoleJava.java
│   ├── Task.java
│   ├── TaskManager.java
│   ├── CommandParser.java
│   └── FileHandler.java
├── src/test/java/Solution/          # Test source code
│   ├── TaskTest.java
│   ├── TaskManagerTest.java
│   ├── CommandParserTest.java
│   ├── FileHandlerTest.java
│   └── TaskManagementConsoleJavaTest.java
├── pom.xml                          # Maven configuration
├── tasks.csv                        # Data file (created at runtime)
└── README.md                        # Project documentation
```

#### Data File Location
- **Primary Data File**: `tasks.csv` in application working directory
- **File Format**: Comma-separated values with UTF-8 encoding
- **Backup Recommendation**: Regular backup of `tasks.csv` file

## User Guide

### Getting Started

#### Initial Launch
1. Start the application using one of the methods above
2. Observe the welcome screen:
   ```
   ========================================
      TASK MANAGEMENT CONSOLE APPLICATION
      Version: V-1.0.0
   ========================================
   
   Type 'help' for available commands.
   ```

3. The application automatically loads existing tasks from `tasks.csv` if available
4. A command prompt (`>`) appears, indicating the application is ready for input

#### First Task Creation
```bash
> create "Complete project documentation", HIGH, 2024-12-31
Task created successfully:
ID: 1 | Description: Complete project documentation | Priority: HIGH   | Due: 2024-12-31 | Status: To Do
```

#### Viewing Tasks
```bash
> list

=== Tasks ===
To Do: 1 | Doing: 0 | Done: 0 | Total: 1
--------------------------------------------------------------------------------
ID: 1 | Description: Complete project documentation | Priority: HIGH   | Due: 2024-12-31 | Status: To Do
--------------------------------------------------------------------------------
```

### Basic Operations

#### Creating Tasks
**Syntax**: `create "description", PRIORITY, YYYY-MM-DD`

**Examples**:
```bash
> create "Submit weekly report", MEDIUM, 2024-03-15
> create "Buy groceries", LOW, 2024-03-10
> create "Prepare presentation for quarterly review", HIGH, 2024-03-20
```

**Validation Rules**:
- Description must be non-empty
- Priority must be LOW, MEDIUM, or HIGH (case-insensitive)
- Due date must be in YYYY-MM-DD format
- Due date cannot be earlier than current date

#### Viewing Tasks
**View All Tasks**:
```bash
> list
```

**Filter by Status**:
```bash
> list "To Do"
> list Doing
> list Done
```

#### Updating Tasks
**Update Status**:
```bash
> update status 1 Doing
> update status 1 Done
> update status 1 "To Do"
```

**Update Description**:
```bash
> update desc 1 "Complete project documentation with detailed examples"
```

**Update Priority**:
```bash
> update priority 1 MEDIUM
```

**Update Due Date**:
```bash
> update due 1 2024-12-15
```

#### Deleting Tasks
**Remove Single Task**:
```bash
> remove 1
Task 1 removed successfully.
```

#### Utility Commands
**Clear Screen**:
```bash
> clear
Console cleared. Task data preserved in memory.
```

**Show Help**:
```bash
> help
```

**Exit Application**:
```bash
> exit
Tasks saved to file.
Application terminated. Goodbye!
```

## Command Reference

### Task Management Commands

#### CREATE Command
Creates a new task with specified attributes.

**Syntax**:
```
create "description", PRIORITY, YYYY-MM-DD
```

**Aliases**: `add`

**Parameters**:
- `description`: Task description (use quotes for multi-word descriptions)
- `PRIORITY`: Importance level (LOW, MEDIUM, HIGH)
- `YYYY-MM-DD`: Due date in ISO format

**Examples**:
```bash
> create "Complete assignment", HIGH, 2024-12-15
> create "Team meeting", MEDIUM, 2024-03-25
```

**Error Messages**:
```
Error: Create command requires: description, priority, dueDate (yyyy-MM-dd)
Error: Invalid priority. Valid values are: LOW, MEDIUM, HIGH
Error: Invalid date format. Please use yyyy-MM-dd format
Error: Due date cannot be earlier than current date: 2024-03-14
```

#### UPDATE Command
Modifies existing task attributes.

**Syntax**:
```
update SUBCOMMAND TASK_ID VALUE
```

**Aliases**: `change`

**Subcommands**:
- `status`: Update task status (To Do, Doing, Done)
- `desc` or `description`: Update task description
- `priority`: Update task priority (LOW, MEDIUM, HIGH)
- `due` or `duedate`: Update due date (YYYY-MM-DD)

**Examples**:
```bash
> update status 1 Doing
> update desc 2 "Updated meeting agenda"
> update priority 3 HIGH
> update due 1 2024-04-01
```

**Error Messages**:
```
Error: Update command requires subcommand: status, desc, priority, or due
Error: Task with ID 999 not found
Error: Invalid status. Valid values are: To Do, Doing, Done
```

#### REMOVE Command
Deletes a task from the system.

**Syntax**:
```
remove TASK_ID
```

**Aliases**: `delete`

**Examples**:
```bash
> remove 1
> delete 3
```

**Behavior**:
- Removes task from memory immediately
- Task ID is permanently removed (not reused)
- Operation is irreversible

**Error Messages**:
```
Error: Remove command requires: taskId
Error: Task ID must be a number
Task 999 not found.
```

### Viewing Commands

#### LIST Command
Displays tasks with optional filtering.

**Syntax**:
```
list [STATUS_FILTER]
```

**Aliases**: `show`

**Examples**:
```bash
# Show all tasks
> list

# Filter by status
> list "To Do"
> list Doing
> list Done
```

**Output Format**:
```
=== Tasks ===
To Do: 3 | Doing: 2 | Done: 5 | Total: 10
--------------------------------------------------------------------------------
ID: 1 | Description: Complete assignment     | Priority: HIGH   | Due: 2024-12-15 | Status: To Do
ID: 2 | Description: Team meeting           | Priority: MEDIUM | Due: 2024-03-25 | Status: Doing
--------------------------------------------------------------------------------
```

**Empty State**:
```
No tasks found.
```

#### HELP Command
Displays comprehensive command reference.

**Syntax**:
```
help
```

**Output**: Detailed help information including:
- Complete list of available commands
- Syntax examples for each command
- Parameter explanations
- Command aliases

### Utility Commands

#### CLEAR Command
Clears the console screen while preserving all task data.

**Syntax**:
```
clear
```

**Aliases**: `cls`

**Behavior**:
- Clears terminal screen using platform-appropriate method
- Preserves all task data in memory
- Maintains application state
- Provides confirmation message

**Platform Implementation**:
- Windows: Uses `cls` command via ProcessBuilder
- Unix/Linux/macOS: Uses ANSI escape sequences
- Fallback: Prints blank lines if native clearing fails

**Examples**:
```bash
> clear
Console cleared. Task data preserved in memory.
```

#### EXIT Command
Terminates the application and saves all tasks to file.

**Syntax**:
```
exit
```

**Aliases**: `quit`

**Behavior**:
1. Saves all tasks to `tasks.csv`
2. Closes input scanner
3. Releases system resources
4. Displays termination message

**Examples**:
```bash
> exit
Tasks saved to file.
Application terminated. Goodbye!
```

## Data Model Specification

### Task Entity Definition

#### Core Attributes
Each task in the system is defined by five mandatory attributes:

1. **ID (Identifier)**
   - Type: Integer (auto-incremented, starting from 1)
   - Uniqueness: Guaranteed unique within application session
   - Persistence: Maintained across application restarts via CSV
   - Constraints: Read-only after creation

2. **Description**
   - Type: String (1 to 1000 characters)
   - Validation: Cannot be null, empty, or whitespace-only
   - Storage: Commas escaped as semicolons in CSV
   - Display: Truncated to 30 characters with ellipsis in list view

3. **Priority**
   - Type: Enumerated (LOW, MEDIUM, HIGH)
   - Default: None (must be specified at creation)
   - Validation: Case-insensitive string conversion
   - Display: Right-aligned in 6-character field

4. **Due Date**
   - Type: LocalDate (Java 8 Date API)
   - Format: ISO 8601 (YYYY-MM-DD)
   - Validation: Must be valid date, cannot be earlier than current date
   - Display: YYYY-MM-DD in all outputs

5. **Status**
   - Type: Enumerated (TODO, DOING, DONE)
   - Default: TODO (when task is created)
   - Display: "To Do", "Doing", "Done" (user-facing names)
   - State Transitions: Any status can transition to any other status

### Status Management System

#### Status Enumeration
**Internal Representation**:
```java
public enum Status
{
    TODO("To Do"),     // Internal: TODO, Display: "To Do"
    DOING("Doing"),    // Internal: DOING, Display: "Doing"
    DONE("Done");      // Internal: DONE, Display: "Done"
}
```

#### Status Transition Rules
- **Initial State**: All tasks created with TODO status
- **Allowed Transitions**: Any status can change to any other status
- **Validation**: Status values are strictly validated against enumeration
- **Business Meaning**:
  - TODO: Task not yet started
  - DOING: Task in progress
  - DONE: Task completed

### Priority System

#### Priority Levels Definition
```java
public enum Priority
{
    LOW,      // Non-urgent, routine tasks
    MEDIUM,   // Important tasks with flexible deadlines
    HIGH      // Critical tasks requiring immediate attention
}
```

#### Priority Assignment Guidelines
- **HIGH**: Critical tasks with immediate deadlines (within 3 days)
- **MEDIUM**: Important tasks with near-term deadlines (1-2 weeks)
- **LOW**: Routine tasks or items without urgent deadlines

## Data Persistence

### File Storage System

#### File Operations
The application stores data in a CSV file named `tasks.csv` in the application directory.

**Loading Process**:
1. Check for file existence at startup
2. Parse CSV line by line
3. Validate each field
4. Recreate task objects in memory
5. Update ID counter to prevent conflicts

**Saving Process**:
1. Convert all tasks to CSV format
2. Write to file with error handling
3. Provide confirmation of save operation

#### File Handler Implementation
```java
public class FileHandler
{
    // Save tasks to file
    public void saveTasksToFile(List<Task> tasks, String filename) throws IOException
    
    // Load tasks from file
    public List<Task> loadTasksFromFile(String filename) throws IOException
    
    // Check file existence
    public boolean fileExists(String filename)
}
```

### CSV Format Specification

#### Field Definitions
**CSV Structure**:
```
ID,Description,Priority,DueDate,Status
```

**Field Details**:
1. **ID**: Integer (plain number, auto-incremented)
2. **Description**: String (commas escaped as semicolons)
3. **Priority**: Enum (LOW, MEDIUM, HIGH - uppercase in storage)
4. **DueDate**: Date (YYYY-MM-DD, ISO 8601 format)
5. **Status**: Enum (TODO, DOING, DONE - internal enum names)

**Example CSV Content**:
```
1,Complete project documentation,HIGH,2024-12-31,TODO
2,Submit weekly report,MEDIUM,2024-03-15,DOING
3,Buy groceries,LOW,2024-03-10,DONE
4,Task; with; escaped; commas,HIGH,2024-12-25,TODO
```

#### CSV Escaping Rules
- **Commas in Descriptions**: Replaced with semicolons in CSV storage
- **Process**: `description.replace(",", ";")` on save, reverse on load
- **Impact**: Semicolons in original descriptions would be corrupted

## Error Handling System

### Error Categories

#### Input Validation Errors
- **Invalid Command Format**: Missing parameters, incorrect syntax
- **Invalid Date Format**: Date not in YYYY-MM-DD format
- **Past Due Date**: Date earlier than current system date
- **Invalid Priority**: Value not in LOW, MEDIUM, HIGH
- **Invalid Status**: Value not in To Do, Doing, Done
- **Non-existent Task ID**: Task ID not found in system

#### File Operation Errors
- **File Access Errors**: Permission issues, file locks, disk full
- **File Format Errors**: Corrupted or malformed CSV file
- **Data Integrity Errors**: Inconsistent data state

#### Runtime Errors
- **Memory Errors**: Insufficient heap space for large datasets
- **System Errors**: Operating system or JVM issues

### Error Messages

#### Message Format
Error messages follow a clear format with specific details and suggested actions.

**Examples**:
```
Error: Invalid date format. Please use yyyy-MM-dd format. Example: 2024-12-31
Error: Due date cannot be earlier than current date: 2024-03-14
Error: Invalid priority. Valid values are: LOW, MEDIUM, HIGH
Error: Task with ID 999 not found
Warning: Could not save tasks to file: Access denied
```

#### Error Recovery Procedures

**User-Level Recovery**:
1. Read the error message carefully
2. Note the specific validation failure
3. Re-enter command with corrected values
4. Use `help` command for syntax reference if needed

**System-Level Recovery**:
1. **File Read Errors**: Start with empty task list, inform user
2. **File Write Errors**: Preserve existing file, report warning
3. **Input Errors**: Reset parser state, await new command

## Testing Framework

### Unit Testing Strategy

#### Testing Philosophy
The application includes a comprehensive JUnit 4 test suite with 247 unit tests 
covering all components.

**Test Principles**:
1. **High Coverage**: Aim for >90% code coverage across all components
2. **Isolation**: Each test is independent and doesn't rely on external systems
3. **Comprehensive**: Test positive, negative, and edge cases
4. **Fast Execution**: Tests run quickly to enable frequent execution

#### Test Tools and Frameworks
- **Primary Framework**: JUnit 4.13.2
- **Build Tool**: Apache Maven with Surefire plugin
- **Assertion Methods**: Standard JUnit assertions (assertEquals, assertTrue, etc.)
- **Test Annotations**: @Test, @Before, @After, @BeforeClass, @AfterClass

### Test Organization

#### Test Structure
**Test Directory**:
```
src/test/java/Solution/
├── TaskTest.java              # 87 tests - Task entity unit tests
├── TaskManagerTest.java       # 46 tests - Business logic tests
├── CommandParserTest.java     # 42 tests - Command parsing tests
├── FileHandlerTest.java       # 41 tests - File I/O tests
└── TaskManagementConsoleJavaTest.java  # 31 tests - Integration tests
```

#### Test Class Template
```java
package Solution;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * JUnit 4 test class for [Class Name].
 */
public class ClassNameTest
{
    private ClassName instance;
    
    @Before
    public void setUp()
    {
        instance = new ClassName();
    }
    
    @After
    public void tearDown()
    {
        // Cleanup
    }
    
    @Test
    public void testMethodNameValid()
    {
        // Arrange, Act, Assert
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMethodNameInvalid()
    {
        // Test that throws expected exception
    }
}
```

#### Test Categories

**TaskTest.java (87 tests)**:
- Task creation with valid parameters
- Task creation with invalid parameters (null, empty, past dates)
- Setter validation tests
- Priority and Status enum conversion tests
- toString and toCSV format tests
- CSV parsing and serialization tests
- ID counter reset tests

**TaskManagerTest.java (46 tests)**:
- Task creation and retrieval
- Task updates (status, description, priority, due date)
- Task removal
- Task filtering by status
- Task sorting by due date
- Task statistics (counts)
- Error handling for invalid operations

**CommandParserTest.java (42 tests)**:
- Command parsing with various inputs
- Argument validation for each command type
- Error handling for invalid commands
- ParsedCommand getter tests
- Complex command parsing scenarios

**FileHandlerTest.java (41 tests)**:
- File save and load operations
- CSV format validation
- Error handling for file operations
- File existence checking
- CSV escaping and unescaping
- Round-trip save/load verification

**TaskManagementConsoleJavaTest.java (31 tests)**:
- Application startup and welcome message
- Command processing and routing
- Error message display
- Console output verification
- Integration workflow tests

### Running Tests

#### Maven Test Commands
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskTest

# Run tests with detailed output
mvn test -Dmaven.test.failure.ignore=true

# Skip tests during build
mvn package -DskipTests
```

#### Test Reports
- **Location**: `target/surefire-reports/`
- **Format**: XML and text reports
- **Content**: Test results, failures, errors, timing
- **Files**: `TEST-*.xml` and `*.txt` for each test class

### Test Coverage

#### Coverage Metrics
- **Total Tests**: 247 unit tests
- **Line Coverage**: >90% estimated across all components
- **Test Distribution**:
  - Task.java: 87 tests (~95% coverage)
  - TaskManager.java: 46 tests (~90% coverage)
  - CommandParser.java: 42 tests (~85% coverage)
  - FileHandler.java: 41 tests (~80% coverage)
  - TaskManagementConsoleJava.java: 31 tests (~75% coverage)

#### Test Examples

**Task Creation Tests**:
```java
@Test
public void testTaskCreationValid()
{
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    Task task = new Task("Test Task", Task.Priority.MEDIUM, tomorrow);
    
    assertNotNull("Task should not be null", task);
    assertEquals("Description should match", "Test Task", task.getDescription());
    assertEquals("Priority should be MEDIUM", Task.Priority.MEDIUM, task.getPriority());
    assertEquals("Due date should be tomorrow", tomorrow, task.getDueDate());
    assertEquals("Default status should be TODO", Task.Status.TODO, task.getStatus());
}

@Test(expected = IllegalArgumentException.class)
public void testTaskCreationPastDueDate()
{
    LocalDate yesterday = LocalDate.now().minusDays(1);
    new Task("Test Task", Task.Priority.MEDIUM, yesterday);
}
```

**Command Parsing Tests**:
```java
@Test
public void testParseCommandWithArguments()
{
    CommandParser parser = new CommandParser();
    ParsedCommand result = parser.parse("create \"Task 1\", HIGH, 2024-12-31");
    
    assertNotNull("Parsed command should not be null", result);
    assertEquals("Command should be 'create'", "create", result.getCommand());
    assertEquals("Arguments should match", "\"Task 1\", HIGH, 2024-12-31", result.getArguments());
}
```

**File Handler Tests**:
```java
@Test
public void testSaveAndLoadTasks() throws IOException
{
    FileHandler fileHandler = new FileHandler();
    String testFile = "test_tasks.csv";
    
    // Create test tasks
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task("Task 1", Task.Priority.LOW, LocalDate.now().plusDays(1)));
    tasks.add(new Task("Task 2", Task.Priority.MEDIUM, LocalDate.now().plusDays(2)));
    
    // Save to file
    fileHandler.saveTasksToFile(tasks, testFile);
    
    // Load from file
    List<Task> loadedTasks = fileHandler.loadTasksFromFile(testFile);
    
    assertEquals("Should load same number of tasks", tasks.size(), loadedTasks.size());
    
    // Cleanup
    Files.deleteIfExists(Paths.get(testFile));
}
```

## Development Guide

### Code Organization

#### Package Structure
**Main Package**: `Solution`
- `TaskManagementConsoleJava.java`: Main application controller
- `Task.java`: Data model with validation
- `TaskManager.java`: Business logic core
- `CommandParser.java`: Input parsing and validation
- `FileHandler.java`: File I/O operations

**Test Package**: `Solution` (under `src/test/java`)
- `TaskTest.java`: Task entity unit tests
- `TaskManagerTest.java`: Business logic tests
- `CommandParserTest.java`: Command parsing tests
- `FileHandlerTest.java`: File I/O tests
- `TaskManagementConsoleJavaTest.java`: Integration tests

#### Class Responsibilities
- **TaskManagementConsoleJava**: Application entry point, user interaction
- **Task**: Data representation and validation
- **TaskManager**: Task collection management and business operations
- **CommandParser**: User input parsing and validation
- **FileHandler**: File system operations and persistence

### Build System

#### Maven Configuration
**pom.xml**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>Naoyuki-Christopher-H</groupId>
    <artifactId>task-management-console-java</artifactId>
    <version>V-1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.version>4.13.2</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>Solution.TaskManagementConsoleJava</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
                <configuration>
                    <includes>
                        <include>**/*Test.java</include>
                    </includes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

#### Build Commands
```bash
# Compile only
mvn compile

# Run tests
mvn test

# Create JAR package
mvn package

# Clean build
mvn clean package
```

### Coding Standards

#### Code Style Requirements
1. **Allman Style Braces**:
   ```java
   public class Example
   {
       public void method()
       {
           if (condition)
           {
               // Code block
           }
       }
   }
   ```

2. **Naming Conventions**:
   - Classes: PascalCase (`TaskManager`)
   - Methods: camelCase (`createTask`)
   - Variables: camelCase (`taskDescription`)
   - Constants: UPPER_SNAKE_CASE (`MAX_TASKS`)
   - Enums: PascalCase (`Priority.HIGH`)

3. **Documentation Standards**:
   - All public methods require Javadoc comments
   - Complex logic requires inline comments
   - Method headers include parameter and return descriptions

#### Error Handling Standards
```java
// Use specific exception types
throw new IllegalArgumentException("Description cannot be empty");

// Provide meaningful error messages
throw new IOException("Failed to save tasks to file: " + filename);

// Handle exceptions appropriately
try
{
    fileOperation();
}
catch (IOException e)
{
    System.err.println("Error: " + e.getMessage());
    // Continue with default behavior
}
```

## Performance Considerations

### Memory Management

#### Object Creation Patterns
**Task Object Optimization**:
```java
public class Task
{
    // Use primitive types where possible
    private final int id;  // Instead of Integer
    
    // Use enum for fixed values (memory efficient)
    private Priority priority;  // Instead of String
    
    // LocalDate is lightweight compared to java.util.Date
    private LocalDate dueDate;
}
```

**Collection Management**:
```java
public class TaskManager
{
    // ArrayList provides good performance for most operations
    private final List<Task> tasks;
    
    // Initialize with expected capacity if known
    public TaskManager()
    {
        tasks = new ArrayList<>(100);  // Initial capacity
    }
}
```

#### Memory Usage Estimation
- **Task object**: approximately 40-60 bytes
- **String descriptions**: variable based on length
- **ArrayList overhead**: 4 bytes per reference
- **Total memory for 1000 tasks**: ~50-100 KB
- **Total memory for 10,000 tasks**: ~500-1000 KB

## Security Considerations

### Input Validation

#### Comprehensive Validation Strategy
**Command Input Validation**:
```java
public class CommandParser
{
    public ParsedCommand parse(String input)
    {
        if (input == null || input.trim().isEmpty())
        {
            throw new IllegalArgumentException("Command cannot be empty");
        }
        
        // Limit input length
        if (input.length() > 1000)
        {
            throw new IllegalArgumentException("Command too long");
        }
    }
}
```

**Task Data Validation**:
```java
public class Task
{
    public Task(String description, Priority priority, LocalDate dueDate)
    {
        // Validate description
        if (description == null || description.trim().isEmpty())
        {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        // Limit description length
        if (description.length() > 1000)
        {
            throw new IllegalArgumentException("Description too long");
        }
    }
}
```

#### Date Validation
```java
public class Task
{
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
    }
}
```

## Troubleshooting Guide

### Common Issues

#### Application Startup Problems
**Issue**: "Java not found" error
```
Error: 'java' is not recognized as an internal or external command
```
**Solution**:
1. Verify Java installation: `java -version`
2. Add Java to PATH environment variable
3. Restart terminal/command prompt

**Issue**: "Main class not found" error
```
Error: Could not find or load main class Solution.TaskManagementConsoleJava
```
**Solution**:
1. Verify classpath includes compiled classes
2. Check package name matches directory structure
3. Recompile with `mvn clean compile`

#### Runtime Issues
**Issue**: Date validation errors
```
Error: Invalid date format. Please use yyyy-MM-dd format
```
**Solution**:
1. Verify date format exactly matches YYYY-MM-DD
2. Check for leading/trailing spaces
3. Ensure month and day are valid (01-12, 01-31)

**Issue**: File permission errors
```
Error: Could not save tasks to file: Access denied
```
**Solution**:
1. Check file/folder permissions
2. Ensure file is not open in another program
3. Run as administrator if needed

#### Data Corruption Issues
**Issue**: CSV file corruption
```
Error parsing line X: Invalid CSV format
```
**Solution**:
1. Backup corrupted file
2. Open file in text editor to inspect
3. Look for missing commas or quotes
4. Remove or fix corrupted lines
5. Restore from backup if available

## Limitations and Constraints

### Technical Limitations

#### Memory Constraints
- **Maximum Tasks**: Limited by available heap memory
- **Practical Limit**: Approximately 10,000-100,000 tasks depending on description length
- **Performance Impact**: Large datasets may slow down operations
- **File Size**: CSV file grows linearly with number of tasks

#### Functional Limitations
- **No Undo/Redo**: Operations cannot be undone
- **Limited Search**: Basic filtering by status only
- **No Attachments**: Cannot attach files to tasks
- **No Notifications**: No reminders or alerts
- **No Reporting**: Limited statistical information

#### User Interface Constraints
- **Console-Based**: No graphical interface available
- **Limited Formatting**: Basic text output only
- **No Mouse Support**: Keyboard-only interaction
- **Fixed Layout**: Display format cannot be customized

#### Integration Constraints
- **Standalone Application**: No API for external integration
- **File-Based Only**: No database connectivity
- **No Web Interface**: Local access only
- **No Mobile Support**: Desktop/laptop only

## Appendix

### Quick Reference Guide

#### Command Quick Reference

**Task Creation**:
```
create "description", priority, yyyy-mm-dd
create "Buy groceries", LOW, 2024-03-15
create "Finish report", HIGH, 2024-03-20
```

**Task Viewing**:
```
list                # Show all tasks
list "To Do"        # Show pending tasks
list Doing          # Show in-progress tasks
list Done           # Show completed tasks
```

**Task Modification**:
```
update status 1 Doing        # Change status
update desc 1 "New text"     # Change description
update priority 1 MEDIUM     # Change priority
update due 1 2024-04-01      # Change due date
```

**Task Deletion**:
```
remove 1            # Delete task with ID 1
delete 2            # Alternative syntax
```

**Utility Commands**:
```
clear               # Clear screen (also: cls)
help                # Show help
exit                # Exit application (also: quit)
```

#### Common Date Formats
- Today: `2024-03-14`
- Tomorrow: `2024-03-15`
- Next Week: `2024-03-21`
- End of Month: `2024-03-31`
- End of Year: `2024-12-31`

#### Priority Values
- `LOW`: Non-urgent tasks
- `MEDIUM`: Important tasks
- `HIGH`: Critical tasks

#### Status Values
- `"To Do"`: Not started
- `Doing`: In progress
- `Done`: Completed

### Technical Specifications

#### System Requirements Summary
```
Minimum Requirements:
- Java Runtime Environment 8
- 512 MB RAM
- 50 MB Disk Space
- Command Line Interface

Recommended Requirements:
- Java Development Kit 8
- 2 GB RAM
- 100 MB Disk Space
- 80-column terminal
```

#### File Format Specification
```
CSV Format:
- Fields: ID,Description,Priority,DueDate,Status
- Separator: Comma (,)
- Text Delimiter: None (commas in description escaped to ;)
- Encoding: UTF-8
- Line Endings: System default
- Header: None
```

#### Performance Specifications
```
Operation           | Small Dataset (100) | Large Dataset (10,000)
--------------------|---------------------|------------------------
Startup Time        | < 100ms             | < 1s
Task Creation       | < 10ms              | < 50ms
Task Listing        | < 50ms              | < 500ms
File Save           | < 100ms             | < 2s
Memory Usage        | < 1MB               | < 50MB
```

#### Test Coverage Summary
```
Component                  | Tests | Coverage
---------------------------|-------|----------
Task.java                  | 87    | ~95%
TaskManager.java           | 46    | ~90%
CommandParser.java         | 42    | ~85%
FileHandler.java           | 41    | ~80%
TaskManagementConsoleJava  | 31    | ~75%
Total                      | 247   | ~90%
```

---