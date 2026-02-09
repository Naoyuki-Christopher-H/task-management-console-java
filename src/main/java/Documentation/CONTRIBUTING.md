# Contributing to Task Management Console Application

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Development Environment Setup](#development-environment-setup)
- [Development Workflow](#development-workflow)
- [Commit Message Guidelines](#commit-message-guidelines)
- [Code Review Process](#code-review-process)
- [Testing Guidelines](#testing-guidelines)
- [Documentation Standards](#documentation-standards)
- [Code Style Guidelines](#code-style-guidelines)
- [Pull Request Process](#pull-request-process)
- [Issue Reporting Guidelines](#issue-reporting-guidelines)
- [Community Guidelines](#community-guidelines)
- [Release Process](#release-process)
- [Frequently Asked Questions](#frequently-asked-questions)

## Introduction

Thank you for your interest in contributing to the Task Management Console 
Application. This document provides guidelines and instructions for contributing 
to the project. By participating in this project, you agree to abide by its terms.

### Project Overview
The Task Management Console Application is a Java-based command-line tool for 
task management. It is built with Java 8, uses Maven for build management, and 
follows object-oriented design principles with comprehensive testing.

### Types of Contributions
We welcome various types of contributions:
- Bug fixes and issue resolution
- New features and enhancements
- Documentation improvements
- Test additions and improvements
- Code refactoring and optimization
- Performance improvements

## Getting Started

### Prerequisites
Before contributing, ensure you have:
1. **Java Development Kit (JDK) 8** or higher installed
2. **Apache Maven 3.6.0** or higher installed
3. **Git** installed and configured
4. A **GitHub account**

### First-time Setup
1. **Fork the Repository**
   - Navigate to the project repository: https://github.com/Naoyuki-Christopher-H/task-management-console-java.git
   - Click the "Fork" button in the top-right corner
   - This creates your personal copy of the repository

2. **Clone Your Fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/task-management-console-java.git
   cd task-management-console-java
   ```

3. **Add Upstream Remote**
   ```bash
   git remote add upstream https://github.com/Naoyuki-Christopher-H/task-management-console-java.git
   ```

4. **Verify Setup**
   ```bash
   # Build the project
   mvn clean compile
   
   # Run tests
   mvn test
   
   # Verify all tests pass
   ```

## Development Environment Setup

### IDE Configuration
We recommend using one of these IDEs with proper Java support:

**IntelliJ IDEA**:
1. Open as Maven project
2. Enable annotation processing
3. Configure Java 8 SDK
4. Install Checkstyle plugin (optional)

**Eclipse**:
1. Import as Maven project
2. Configure Java 8 JRE
3. Enable Save Actions for code formatting

**VS Code**:
1. Install Java Extension Pack
2. Configure Java 8 home
3. Install Maven for Java extension

### Development Tools
Ensure these tools are properly configured:

1. **Git**: Configure your name and email
   ```bash
   git config --global user.name "Your Name"
   git config --global user.email "your.email@example.com"
   ```

2. **Maven**: Configure settings if needed
   ```bash
   # Verify Maven installation
   mvn --version
   ```

3. **Java**: Set JAVA_HOME environment variable
   ```bash
   # Verify Java installation
   java -version
   javac -version
   ```

### Project Structure Familiarization
Familiarize yourself with the project structure:
```
task-management-console-java/
├── src/main/java/Solution/          # Main source code
│   ├── TaskManagementConsoleJava.java
│   ├── Task.java
│   ├── TaskManager.java
│   ├── CommandParser.java
│   └── FileHandler.java
├── src/test/java/Solution/          # Test source code
├── pom.xml                          # Maven configuration
└── README.md                        # Project documentation
```

## Development Workflow

### Branch Strategy
We follow a feature branch workflow:

1. **Main Branches**:
   - `main`: Production-ready code
   - `develop`: Integration branch for features

2. **Supporting Branches**:
   - `feature/*`: New features
   - `bugfix/*`: Bug fixes
   - `hotfix/*`: Critical production fixes
   - `release/*`: Release preparation

### Creating a New Feature

1. **Start from develop branch**
   ```bash
   git checkout develop
   git pull origin develop
   ```

2. **Create feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
   Branch naming conventions:
   - Features: `feature/add-search-command`
   - Bug fixes: `bugfix/file-corruption-issue`
   - Documentation: `docs/update-readme`

3. **Implement your changes**
   - Write code following style guidelines
   - Add tests for new functionality
   - Update documentation as needed

4. **Commit your changes**
   Follow the commit message guidelines detailed below.

5. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create Pull Request**
   - Navigate to your fork on GitHub
   - Click "Compare & pull request"
   - Fill out the pull request template
   - Request review from maintainers

### Keeping Your Branch Updated
Regularly sync with the upstream repository:
```bash
# Fetch upstream changes
git fetch upstream

# Merge upstream develop into your feature branch
git checkout feature/your-feature-name
git merge upstream/develop

# Resolve any conflicts
# Test your changes still work
```

### Completing Your Contribution

1. **Ensure all tests pass**
   ```bash
   mvn clean test
   ```

2. **Update documentation** if needed
3. **Squash commits** if necessary (see below)
4. **Request review** from maintainers
5. **Address review feedback**
6. **Wait for merge approval**

## Commit Message Guidelines

### Commit Structure
Each commit message must follow this structure:
```
Header line: explain the commit in one line (use the imperative)

Body of commit message is a few lines of text, explaining things
in more detail, possibly giving some background about the issue
being fixed, etc etc.

The body of the commit message can be several paragraphs, and
please do proper word-wrap and keep columns shorter than about
74 characters or so. That way "git log" will show things
nicely even when it's indented.

Make sure you explain your solution and why you're doing what you're
doing, as opposed to describing what you're doing. Reviewers and your
future self can read the patch, but might not understand why a
particular solution was implemented.

Reported-by: whoever-reported-it
Signed-off-by: Your Name <your.email@example.com>
```

### Header Line Rules
1. **Use the imperative mood**: "Fix bug" not "Fixed bug" or "Fixes bug"
2. **Capitalize the first letter**
3. **Do not end with a period**
4. **Keep under 72 characters**
5. **Use conventional commit types** (see below)

### Conventional Commit Types
Use one of these prefixes in your header:

| Type | Description | Example |
|------|-------------|---------|
| `feat` | A new feature | `feat: add search command functionality` |
| `fix` | A bug fix | `fix: handle null description in task creation` |
| `docs` | Documentation only changes | `docs: update command reference in README` |
| `style` | Changes that do not affect meaning (white-space, formatting, etc.) | `style: reformat code to follow Allman style` |
| `refactor` | Code change that neither fixes a bug nor adds a feature | `refactor: extract validation logic to separate method` |
| `test` | Adding or correcting tests | `test: add unit tests for file handler` |
| `chore` | Changes to build process or auxiliary tools | `chore: update Maven dependencies` |

### Body Content Guidelines
1. **Explain the "why" not the "what"**: The code shows what changed; explain why it was necessary
2. **Provide context**: Explain the problem being solved
3. **Reference issues**: Link to GitHub issues when applicable
4. **Consider alternatives**: Mention other approaches considered and why they weren't chosen
5. **Note breaking changes**: If any, explain migration steps

### Footer Section
Include any of these as needed:

1. **Breaking Changes**: Start with `BREAKING CHANGE:` followed by description
2. **Issue References**: `Closes #123`, `Fixes #456`, `Related to #789`
3. **Credit**: `Reported-by: User Name <user@example.com>`
4. **Sign-off**: `Signed-off-by: Your Name <your.email@example.com>`

### Examples

#### Good Commit Message
```
feat: add search functionality to list command

Adds the ability to search tasks by keyword in the description
when using the list command. This addresses user requests for
better task discovery in large task lists.

The implementation adds a new optional parameter to the list
command: "list search <keyword>". Results are filtered to show
only tasks containing the keyword in their description
(case-insensitive search).

Considered implementing as a separate command but decided to
extend list command for consistency with status filtering.

Closes #42
Signed-off-by: John Developer <john@example.com>
```

#### Bug Fix Commit
```
fix: prevent null pointer exception in file loading

When loading tasks from a corrupted CSV file, a null pointer
exception could occur if the status field was missing. This fix
adds proper validation before accessing the status field and
provides a clearer error message.

The fix includes:
1. Additional null checks in Task.fromCSV()
2. Improved error message with line number
3. Graceful continuation to load remaining valid tasks

Reported-by: Jane User <jane@example.com>
Signed-off-by: John Developer <john@example.com>
```

#### Documentation Commit
```
docs: update installation instructions in README

Updates the README.md file with clearer installation
instructions for different operating systems. Added specific
commands for Windows, Linux, and macOS users.

Also corrected the Maven command syntax and added troubleshooting
section for common installation issues.

Signed-off-by: John Developer <john@example.com>
```

### Commit Best Practices

1. **Atomic Commits**: Each commit should represent a single logical change
2. **Frequent Commits**: Commit often with clear messages
3. **Test Before Commit**: Always run tests before committing
4. **No Debug Code**: Never commit debugging code or temporary changes
5. **Meaningful Messages**: Write messages that will be useful in 6 months

### Amending Commits
If you need to fix a commit:

```bash
# Make your changes
git add .
git commit --amend  # Edit the commit message
git push --force-with-lease origin feature/your-feature-name
```

**Note**: Only force-push to your own branches, never to shared branches.

### Squashing Commits
For a clean history, squash related commits before finalizing:

```bash
# Interactive rebase (e.g., for last 3 commits)
git rebase -i HEAD~3

# In the editor, change "pick" to "squash" for commits to combine
# Write a new combined commit message
```

## Code Review Process

### Before Requesting Review
Ensure your code is ready for review:

1. **All tests pass**
   ```bash
   mvn clean test
   ```

2. **Code follows style guidelines**
3. **Documentation is updated**
4. **Commit history is clean**
5. **No merge conflicts**

### Creating a Pull Request

1. **Use the PR Template**: Fill out all sections completely
2. **Describe Changes**: Explain what and why
3. **Link Issues**: Reference related issues
4. **Add Labels**: Appropriate labels (bug, enhancement, etc.)
5. **Request Reviewers**: Tag relevant maintainers

### During Review
1. **Be responsive**: Address feedback promptly
2. **Be open**: Consider suggestions objectively
3. **Ask questions**: If feedback is unclear, ask for clarification
4. **Update PR**: Push fixes as new commits (don't squash until final)

### Review Checklist
Reviewers will check:

- [ ] Code follows project conventions
- [ ] Tests are included and pass
- [ ] Documentation is updated
- [ ] No breaking changes (unless intended)
- [ ] Error handling is appropriate
- [ ] Performance considerations addressed
- [ ] Security considerations addressed

### After Approval
1. **Squash commits** if requested
2. **Merge via GitHub**: Use "Squash and merge" for feature branches
3. **Delete branch**: After successful merge

## Testing Guidelines

### Test Philosophy
- Write tests before or alongside code (TDD encouraged)
- Aim for >90% code coverage
- Tests should be independent and repeatable
- Test both positive and negative cases

### Test Structure
Follow existing test patterns:

```java
package Solution;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * JUnit 4 test class for [Class Name].
 * Tests [specific functionality].
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
        // Arrange
        // Act
        // Assert
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMethodNameInvalid()
    {
        // Test that throws expected exception
    }
}
```

### Test Categories
Add tests for:

1. **Unit Tests**: Test individual methods/classes
2. **Integration Tests**: Test component interactions
3. **Edge Cases**: Boundary conditions and error cases
4. **Performance Tests**: For performance-critical code

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskTest

# Run with coverage (if configured)
mvn test jacoco:report
```

### Test Naming Convention
Use descriptive names:
- `test[Method][Scenario]`: `testCreateTaskValid`, `testCreateTaskNullDescription`
- `test[Method]With[Condition]`: `testLoadTasksWithCorruptedFile`
- `test[Method]EdgeCase`: `testUpdateTaskWithMaxDescriptionLength`

## Documentation Standards

### Code Documentation
1. **Javadoc Comments**: All public classes and methods
2. **Inline Comments**: For complex logic (explain why, not what)
3. **TODO Comments**: For future improvements

### Javadoc Format
```java
/**
 * Brief description of class/method.
 * 
 * Detailed explanation of purpose and functionality.
 * 
 * @param parameterName Description of parameter
 * @return Description of return value
 * @throws ExceptionType Circumstances that cause exception
 * @see RelatedClass#methodName
 * @since Version when added
 */
```

### User Documentation
1. **README.md**: Main project documentation
2. **JavaDoc**: API documentation (generate with `mvn javadoc:javadoc`)
3. **In-app help**: `help` command output

### Updating Documentation
When making code changes:
1. Update Javadoc comments
2. Update README if features/commands change
3. Update help text in `TaskManagementConsoleJava.java`
4. Update this CONTRIBUTING.md if process changes

## Code Style Guidelines

### General Style Rules
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

2. **Indentation**: 4 spaces (no tabs)
3. **Line Length**: Maximum 100 characters (80 preferred)
4. **Naming Conventions**:
   - Classes: `PascalCase`
   - Methods: `camelCase`
   - Variables: `camelCase`
   - Constants: `UPPER_SNAKE_CASE`
   - Packages: `lowercase`

### Import Statements
```java
// Group and order imports:
import java.io.*;          // Java standard library
import java.time.*;
import java.util.*;

import org.junit.*;        // External libraries
import static org.junit.Assert.*;

import Solution.*;         // Project classes
```

### Error Handling
```java
// Use specific exceptions
throw new IllegalArgumentException("Description cannot be empty");

// Provide meaningful messages
throw new IOException("Failed to save tasks to file: " + filename);

// Handle exceptions appropriately
try
{
    fileOperation();
}
catch (IOException e)
{
    // Log and recover or rethrow
    System.err.println("Error: " + e.getMessage());
}
```

### Resource Management
```java
// Use try-with-resources for AutoCloseable
try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
{
    // Use resource
}

// Clean up in finally blocks for non-AutoCloseable
Resource resource = null;
try
{
    resource = acquireResource();
    useResource(resource);
}
finally
{
    if (resource != null)
    {
        resource.cleanup();
    }
}
```

## Pull Request Process

### PR Template
All pull requests must use this template:

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update
- [ ] Code refactoring
- [ ] Test addition/update

## Testing
- [ ] Unit tests added/updated
- [ ] All tests pass locally
- [ ] Manual testing performed
- [ ] Test coverage maintained or improved

## Documentation
- [ ] Code comments updated
- [ ] README updated
- [ ] JavaDoc updated
- [ ] In-app help updated

## Code Quality
- [ ] Follows project coding standards
- [ ] No lint warnings
- [ ] No new TODO comments (unless justified)

## Related Issues
Closes #123
Fixes #456
Related to #789

## Checklist
- [ ] My code follows the style guidelines of this project
- [ ] I have performed a self-review of my own code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes
- [ ] Any dependent changes have been merged and published

## Additional Notes
Any additional information reviewers should know
```

### PR Review Timeline
- Initial review within 48 hours
- Follow-up reviews within 24 hours of updates
- Aim to merge within 1 week for small changes

### PR Status Labels
- `ready-for-review`: Ready for maintainer review
- `needs-work`: Requires changes based on review
- `awaiting-author`: Waiting for author response
- `approved`: Ready for merge
- `do-not-merge`: Temporary hold

## Issue Reporting Guidelines

### Before Reporting
1. Check existing issues for duplicates
2. Search documentation for solutions
3. Try to reproduce the issue

### Issue Template
Use this template when creating issues:

```markdown
## Description
Clear and concise description of the issue

## Steps to Reproduce
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

## Expected Behavior
What you expected to happen

## Actual Behavior
What actually happened

## Screenshots/Logs
If applicable, add screenshots or log output

## Environment
- Application Version: [e.g., V-1.0.0]
- Java Version: [e.g., Java 8 Update 201]
- OS: [e.g., Windows 10, Ubuntu 20.04, macOS 11.0]
- Terminal/Shell: [e.g., Command Prompt, Bash, PowerShell]

## Additional Context
Add any other context about the problem here
```

### Issue Labels
- `bug`: Something isn't working
- `enhancement`: New feature or improvement
- `documentation`: Documentation improvements
- `question`: Further information is requested
- `good-first-issue`: Good for newcomers
- `help-wanted`: Extra attention is needed

## Community Guidelines

### Code of Conduct
All contributors must adhere to:

1. **Be respectful**: Treat all community members with respect
2. **Be constructive**: Provide constructive feedback
3. **Be patient**: Allow time for responses and reviews
4. **Be inclusive**: Welcome contributors from all backgrounds

### Communication Channels
- **GitHub Issues**: For bug reports and feature requests
- **GitHub Discussions**: For questions and discussions
- **Pull Requests**: For code contributions

### Recognition
Contributors will be recognized in:
1. Commit history
2. Release notes
3. Contributor list in documentation

## Release Process

### Versioning Scheme
We follow Semantic Versioning (SemVer):
- `MAJOR.MINOR.PATCH` (e.g., `V-1.0.0`)
- `MAJOR`: Incompatible API changes
- `MINOR`: Backwards-compatible functionality
- `PATCH`: Backwards-compatible bug fixes

### Release Checklist
Before each release:

1. [ ] All tests pass
2. [ ] Documentation is updated
3. [ ] Version number is updated
4. [ ] Changelog is prepared
5. [ ] Release notes are written
6. [ ] Tag is created in Git
7. [ ] JAR is built and verified
8. [ ] Release is published on GitHub

### Creating a Release
```bash
# Update version in pom.xml
# Update documentation
# Create release branch
git checkout -b release/v1.1.0

# Final testing
mvn clean test
mvn package

# Create tag
git tag -a v1.1.0 -m "Release version 1.1.0"

# Push tag
git push origin v1.1.0

# Merge to main and develop
```

## Frequently Asked Questions

### Q: How do I start contributing as a beginner?
A: Look for issues labeled `good-first-issue` or `help-wanted`. Start with documentation improvements or small bug fixes.

### Q: What if my pull request gets stuck in review?
A: Be patient and responsive. If a week passes without feedback, politely ask for an update.

### Q: Can I work on multiple features at once?
A: Yes, but use separate branches for each feature. Keep them focused and manageable.

### Q: What if I find a security vulnerability?
A: Do not open a public issue. Contact the maintainers directly through GitHub security features.

### Q: How are controversial decisions made?
A: Through discussion in GitHub issues, with maintainers making final decisions based on community feedback.

### Q: Can I add new dependencies?
A: Generally avoid adding dependencies unless absolutely necessary. If needed, discuss in an issue first.

### Q: What if I need help with Git?
A: Review Git documentation or ask for help in discussions. Be specific about what you're trying to do.

---