# Task Management Console Application

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Usage Guide](#usage-guide)
- [Command Reference](#command-reference)
- [Data Storage](#data-storage)
- [Testing](#testing)
- [Development](#development)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [Support](#support)

## Project Overview

The Task Management Console Application is a Java-based command-line tool designed for
efficient task organization and management. Built with Java 8 and following object-oriented
design principles, this application provides a comprehensive solution for creating, tracking,
and managing tasks through a console interface without external dependencies.

This application is ideal for developers, technical users, and anyone preferring command-line
tools for productivity management. It offers robust validation, automatic data persistence,
and a clean, intuitive interface.

**Repository**: https://github.com/Naoyuki-Christopher-H/task-management-console-java.git

## Features

### Core Capabilities
- **Complete Task Management**: Create, read, update, and delete tasks
- **Advanced Validation**: Comprehensive input validation for dates, priorities, and statuses
- **Automatic Persistence**: CSV-based file storage with automatic loading and saving
- **Cross-Platform Compatibility**: Native operation on Windows, Linux, and macOS
- **Memory-Efficient Architecture**: Optimized in-memory storage with file backup

### User Experience
- **Intuitive Command Interface**: Simple, consistent command syntax with aliases
- **Clear Console Display**: Built-in screen clearing capability
- **Comprehensive Help System**: Detailed command reference and examples
- **Formatted Output**: Well-structured task display with statistics

### Technical Excellence
- **Object-Oriented Design**: Clean separation of concerns and modular architecture
- **Comprehensive Testing**: Complete JUnit 4 test suite with 247 unit tests
- **Code Quality**: Allman style formatting with comprehensive documentation
- **Robust Error Handling**: Informative error messages with recovery guidance

## System Requirements

### Minimum Requirements
- **Java Runtime Environment (JRE) 8** or higher
- **Operating System**: Windows 7+, macOS 10.10+, or Linux (modern distribution)
- **Memory**: 512 MB RAM
- **Storage**: 50 MB available disk space
- **Terminal**: Command-line interface with 80-column display

### Recommended Specifications
- **Java Development Kit (JDK) 8** or higher
- **Memory**: 2 GB RAM
- **Storage**: 100 MB available disk space
- **Terminal**: Modern terminal emulator with UTF-8 support

## Installation

### Option 1: Build from Source

#### Step 1: Clone the Repository
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
After successful build, you'll find the executable JAR at:
```
target/task-management-console-java-V-1.0.0.jar
```

### Option 2: Run Directly with Maven
```bash
# Run without packaging
mvn exec:java -Dexec.mainClass="Solution.TaskManagementConsoleJava"
```

### Option 3: Run from Executable JAR
```bash
java -jar target/task-management-console-java-V-1.0.0.jar
```

## Quick Start

### First-Time Setup

1. **Launch the Application**
   ```bash
   java -jar task-management-console-java-V-1.0.0.jar
   ```

2. **View Available Commands**
   ```
   > help
   ```

3. **Create Your First Task**
   ```
   > create "Complete project documentation", HIGH, 2024-12-31
   ```

4. **View All Tasks**
   ```
   > list
   ```

### Sample Session
```bash
$ java -jar task-management-console-java-V-1.0.0.jar

========================================
   TASK MANAGEMENT CONSOLE APPLICATION
   Version: V-1.0.0
========================================

Type 'help' for available commands.

> create "Submit weekly report", MEDIUM, 2024-03-15
Task created successfully:
ID: 1 | Description: Submit weekly report | Priority: MEDIUM | Due: 2024-03-15 | Status: To Do

> create "Prepare presentation", HIGH, 2024-03-20
Task created successfully:
ID: 2 | Description: Prepare presentation | Priority: HIGH   | Due: 2024-03-20 | Status: To Do

> list

=== Tasks ===
To Do: 2 | Doing: 0 | Done: 0 | Total: 2
--------------------------------------------------------------------------------
ID: 1 | Description: Submit weekly report    | Priority: MEDIUM | Due: 2024-03-15 | Status: To Do
ID: 2 | Description: Prepare presentation    | Priority: HIGH   | Due: 2024-03-20 | Status: To Do
--------------------------------------------------------------------------------

> exit
Tasks saved to file.
Application terminated. Goodbye!
```

## Usage Guide

### Task Attributes

Each task contains the following attributes:

1. **ID**: Unique identifier (auto-generated)
2. **Description**: Text description of the task
3. **Priority**: Importance level (LOW, MEDIUM, HIGH)
4. **Due Date**: Completion deadline (YYYY-MM-DD format)
5. **Status**: Current state (To Do, Doing, Done)

### Data Validation Rules

- **Description**: Cannot be empty, maximum 1000 characters
- **Priority**: Must be LOW, MEDIUM, or HIGH (case-insensitive)
- **Due Date**: Must be in YYYY-MM-DD format, cannot be in the past
- **Status**: Must be To Do, Doing, or Done

### Workflow Example

1. **Planning Phase**: Create tasks with descriptions and priorities
2. **Execution Phase**: Update task status to "Doing" when work begins
3. **Completion Phase**: Update task status to "Done" when completed
4. **Review Phase**: List tasks to review progress and statistics
5. **Cleanup Phase**: Remove completed tasks when no longer needed

## Command Reference

### Task Management Commands

#### CREATE Command
Creates a new task with specified attributes.

**Syntax**:
```
create "description", PRIORITY, YYYY-MM-DD
```

**Aliases**: `add`

**Examples**:
```bash
> create "Complete assignment", HIGH, 2024-12-15
> create "Buy groceries", LOW, 2024-03-20
> create "Schedule team meeting", MEDIUM, 2024-03-25
```

**Parameters**:
- `description`: Task description (use quotes for multi-word descriptions)
- `PRIORITY`: Importance level (LOW, MEDIUM, HIGH)
- `YYYY-MM-DD`: Due date in ISO format

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

### Utility Commands

#### CLEAR Command
Clears the console screen while preserving all task data.

**Syntax**:
```
clear
```

**Aliases**: `cls`

**Examples**:
```bash
> clear
Console cleared. Task data preserved in memory.
```

#### HELP Command
Displays comprehensive command reference.

**Syntax**:
```
help
```

#### EXIT Command
Terminates the application and saves all tasks to file.

**Syntax**:
```
exit
```

**Aliases**: `quit`

**Examples**:
```bash
> exit
Tasks saved to file.
Application terminated. Goodbye!
```

## Data Storage

### File System

The application stores data in a CSV file named `tasks.csv` in the application directory.

### File Format

```
ID,Description,Priority,DueDate,Status
1,Complete project documentation,HIGH,2024-12-31,TODO
2,Submit weekly report,MEDIUM,2024-03-15,DOING
3,Buy groceries,LOW,2024-03-10,DONE
```

### Data Persistence

- **Automatic Loading**: Tasks are loaded from `tasks.csv` at application startup
- **Automatic Saving**: Tasks are saved to `tasks.csv` when application exits
- **Data Integrity**: File operations include validation and error recovery
- **Backup**: Consider regular backups of the `tasks.csv` file

## Testing

### Test Suite

The application includes a comprehensive JUnit 4 test suite with 247 unit tests:

- **TaskTest.java**: 87 tests - Task entity unit tests
- **TaskManagerTest.java**: 46 tests - Business logic tests
- **CommandParserTest.java**: 42 tests - Command parsing tests
- **FileHandlerTest.java**: 41 tests - File I/O tests
- **TaskManagementConsoleJavaTest.java**: 31 tests - Integration tests

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskTest

# Generate test reports
mvn surefire-report:report
```

### Test Reports

Test reports are generated in:
```
target/surefire-reports/
```

## Development

### Building the Project

```bash
# Clean and build
mvn clean compile

# Create JAR file
mvn package

# Install to local repository
mvn install
```

### Code Standards

- **Braces**: Allman style (opening braces on new lines)
- **Naming**: PascalCase for classes, camelCase for methods and variables
- **Documentation**: Javadoc comments for all public methods
- **Error Handling**: Use specific exception types with informative messages

### Project Structure

```
task-management-console-java/
├── src/main/java/Solution/                   # Source code
│   ├── TaskManagementConsoleJava.java        # Main application controller
│   ├── Task.java                             # Data model with validation
│   ├── TaskManager.java                      # Business logic core
│   ├── CommandParser.java                    # Input parsing and validation
│   └── FileHandler.java                      # File I/O operations
├── src/test/java/Solution/                   # Test source code
│   ├── TaskTest.java                         # 87 tests - Task entity unit tests
│   ├── TaskManagerTest.java                  # 46 tests - Business logic tests
│   ├── CommandParserTest.java                # 42 tests - Command parsing tests
│   ├── FileHandlerTest.java                  # 41 tests - File I/O tests
│   └── TaskManagementConsoleJavaTest.java    # 31 tests - Integration tests
├── pom.xml                                   # Maven configuration
├── tasks.csv                                 # Data file (created at runtime)
└── README.md                                 # This documentation file
```

### Key Classes

1. **TaskManagementConsoleJava**: Main application controller handling user interaction
2. **Task**: Data model representing a task entity with validation logic
3. **TaskManager**: Business logic layer for task management operations
4. **CommandParser**: Input validation and command processing component
5. **FileHandler**: Data persistence layer for file operations

## Project Structure

### Source Code Organization

**Main Classes**:
- `TaskManagementConsoleJava.java`: Application entry point and controller
- `Task.java`: Task data model with validation and CSV serialization
- `TaskManager.java`: Business logic for task operations
- `CommandParser.java`: Command parsing and validation
- `FileHandler.java`: File I/O for data persistence

**Test Classes**:
- `TaskTest.java`: Comprehensive unit tests for Task class
- `TaskManagerTest.java`: Unit tests for business logic
- `CommandParserTest.java`: Tests for command parsing
- `FileHandlerTest.java`: Tests for file operations
- `TaskManagementConsoleJavaTest.java`: Integration tests

### Build Configuration

**pom.xml**:
- Java 8 compatibility
- JUnit 4.13.2 dependency for testing
- Maven plugins for compilation, packaging, and testing
- Executable JAR configuration with main class specification

### Data Files

- `tasks.csv`: Primary data storage (created automatically)
- Test data files: Created and cleaned up during testing

## Contributing

### How to Contribute

1. **Fork the Repository**
   - Click the "Fork" button on GitHub
   - Clone your forked repository locally

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing coding standards
   - Add tests for new functionality
   - Update documentation as needed

4. **Test Your Changes**
   ```bash
   mvn clean test
   mvn package
   ```

5. **Submit a Pull Request**
   - Push your changes to your fork
   - Create a pull request to the main repository
   - Describe your changes and their purpose

### Contribution Guidelines

- **Code Quality**: Maintain existing code standards and quality
- **Testing**: Include tests for new features and bug fixes
- **Documentation**: Update README and documentation as needed
- **Commit Messages**: Use clear, descriptive commit messages
- **Scope**: Keep changes focused and manageable

## Support

### Getting Help

- **Documentation**: Start with this README
- **GitHub Issues**: Report bugs or request features via GitHub Issues
- **GitHub Repository**: https://github.com/Naoyuki-Christopher-H/task-management-console-java.git

### Common Issues and Solutions

#### Application Won't Start
```
Error: Could not find or load main class Solution.TaskManagementConsoleJava
```
**Solution:** Ensure you're running from the correct directory and the JAR file is properly built.

#### File Permission Errors
```
Error: Could not save tasks to file: Access denied
```
**Solution:** Check file permissions and ensure the application has write access to the directory.

#### Date Format Errors
```
Error: Invalid date format. Please use yyyy-MM-dd format
```
**Solution:** Use exact YYYY-MM-DD format (e.g., 2024-12-31).

### Reporting Issues

When reporting issues, please include:
1. **Application Version**: V-1.0.0
2. **Java Version**: Output of `java -version`
3. **Operating System**: Windows/Linux/macOS version
4. **Steps to Reproduce**: Clear sequence of actions
5. **Expected Behavior**: What should happen
6. **Actual Behavior**: What actually happens
7. **Error Messages**: Complete error output

---
