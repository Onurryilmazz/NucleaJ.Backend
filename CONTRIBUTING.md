# Contributing to NucleaJ Backend

First off, thank you for considering contributing to NucleaJ Backend! It's people like you that make it such a great tool.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* **Use a clear and descriptive title**
* **Describe the exact steps to reproduce the problem**
* **Provide specific examples to demonstrate the steps**
* **Describe the behavior you observed and what you expected**
* **Include logs and screenshots if applicable**
* **Specify your environment** (OS, Java version, etc.)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

* **Use a clear and descriptive title**
* **Provide a detailed description of the suggested enhancement**
* **Explain why this enhancement would be useful**
* **List any alternative solutions you've considered**

### Pull Requests

1. Fork the repository
2. Create a new branch from `main`:
   ```bash
   git checkout -b feature/my-new-feature
   ```
3. Make your changes
4. Write or update tests as necessary
5. Ensure all tests pass:
   ```bash
   mvn test
   ```
6. Update documentation as needed
7. Commit your changes:
   ```bash
   git commit -m "feat: add amazing feature"
   ```
8. Push to your fork:
   ```bash
   git push origin feature/my-new-feature
   ```
9. Open a Pull Request

## Development Setup

### Prerequisites
- Java 21 or higher
- Maven 3.9+
- SQL Server 2019+
- Git

### Setup Steps

1. Clone your fork:
   ```bash
   git clone https://github.com/Onurryilmazz/nucleaj-backend.git
   cd nucleaj-backend
   ```

2. Copy the example configuration:
   ```bash
   cp nucleaj-api/src/main/resources/application-local.properties.example \
      nucleaj-api/src/main/resources/application-local.properties
   ```

3. Update configuration with your local settings

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run tests:
   ```bash
   mvn test
   ```

## Coding Standards

### Java Style Guide

* Follow standard Java naming conventions
* Use meaningful variable and method names
* Keep methods small and focused (single responsibility)
* Add JavaDoc for public methods and classes
* Maximum line length: 120 characters

### Code Organization

```java
// 1. Package declaration
package com.nuclea.api.service;

// 2. Imports (organized)
import java.util.*;
import com.nuclea.*;
import org.springframework.*;

// 3. Class JavaDoc
/**
 * Service for handling customer operations.
 */
// 4. Class annotations
@Service
@RequiredArgsConstructor
public class CustomerService {

    // 5. Fields (private final preferred)
    private final CustomerRepository repository;

    // 6. Constructors
    // 7. Public methods
    // 8. Private methods
}
```

### Lombok Usage

Prefer Lombok annotations to reduce boilerplate:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String firstName;
    private String lastName;
}
```

### Testing

* Write tests for all new features
* Maintain or improve test coverage
* Use meaningful test names:
  ```java
  @Test
  void registerCustomer_WithValidData_ShouldReturnSuccess() {
      // Arrange
      // Act
      // Assert
  }
  ```

### Git Commit Messages

Follow conventional commits:

* `feat:` - New feature
* `fix:` - Bug fix
* `docs:` - Documentation changes
* `style:` - Code style changes (formatting)
* `refactor:` - Code refactoring
* `test:` - Adding or updating tests
* `chore:` - Build process or auxiliary tool changes

Examples:
```
feat: add email verification feature
fix: resolve JWT token expiration bug
docs: update README with new configuration options
```

## Project Structure Guidelines

### Adding New Modules

When adding a new module:
1. Follow the existing naming convention: `nucleaj-[module-name]`
2. Add module to parent `pom.xml`
3. Create appropriate package structure
4. Update README.md

### Adding New Entities

1. Create entity in `nucleaj-data/src/main/java/com/nuclea/data/entity/[category]/`
2. Implement appropriate interfaces (`ISoftDelete`, etc.)
3. Create repository in matching package structure
4. Add indexes for frequently queried fields
5. Write tests

### Adding New Services

1. Create interface in `[module]/service/` package
2. Implement service extending `BaseService` if applicable
3. Use appropriate service marker interface
4. Add unit tests

## Documentation

* Update README.md for significant changes
* Add JavaDoc for public APIs
* Update OpenAPI/Swagger annotations
* Include code examples where helpful

## Testing Guidelines

### Unit Tests
```java
@Test
void methodName_Condition_ExpectedBehavior() {
    // Arrange
    var input = createTestData();

    // Act
    var result = service.methodName(input);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.isSuccess()).isTrue();
}
```

### Integration Tests
```java
@SpringBootTest
@AutoConfigureMockMvc
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testEndpoint() throws Exception {
        mockMvc.perform(post("/api/endpoint")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
}
```

## Review Process

1. All Pull Requests require review
2. Ensure CI/CD checks pass
3. Address all review comments
4. Maintain clean commit history
5. Rebase if necessary to keep history clean

## Questions?

Feel free to open an issue with the `question` label if you have any questions about contributing.

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

**Thank you for contributing to NucleaJ Backend!** 🚀
