# Task Management System API

A robust REST API for task management built with Spring Boot 3.5.14, featuring CRUD operations, pagination, filtering, and comprehensive error handling.

## Overview

The Task Management System API provides a complete backend solution for managing tasks with the following features:

- **CRUD Operations**: Create, Read, Update, and Delete tasks
- **Pagination**: Efficiently retrieve tasks in pages
- **Filtering**: Filter tasks by status (PENDING, IN_PROGRESS, DONE)
- **Validation**: Comprehensive input validation with meaningful error messages
- **Error Handling**: Global exception handling with consistent error responses
- **In-Memory Storage**: No database required (perfect for development and testing)
- **Unit & Integration Tests**: Comprehensive test coverage

## Project Structure

```
src/
├── main/
│   ├── java/com/taskmanager/taskmanagementapi/
│   │   ├── controller/          # REST Controllers
│   │   ├── service/             # Business Logic Layer
│   │   ├── repository/          # Data Access Layer (In-Memory)
│   │   ├── entity/              # Domain Models
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── enums/               # Enumerations (TaskStatus)
│   │   ├── exception/           # Custom Exceptions
│   │   ├── util/                # Utility Classes
│   │   └── TaskManagementSystemApiApplication.java  # Main App
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/taskmanager/taskmanagementapi/
        ├── service/             # Unit Tests
        └── controller/          # Integration Tests
```

## Architecture

### Layered Architecture

```
┌─────────────────────┐
│   REST Controller   │  Handles HTTP Requests/Responses
├─────────────────────┤
│   Service Layer     │  Business Logic & Validation
├─────────────────────┤
│   Repository Layer  │  Data Access (In-Memory Store)
├─────────────────────┤
│   Entity/DTO        │  Data Models
└─────────────────────┘
```

### Key Components

1. **Entity Layer**: `Task` entity with unique ID, title, description, status, and due date
2. **Repository Layer**: In-memory repository using `Map<String, Task>`
3. **Service Layer**: Business logic with validation
4. **Controller Layer**: REST endpoints
5. **Exception Handling**: Global exception handler for consistent error responses

## Prerequisites

### System Requirements

- **Java**: JDK 21 or later
- **Maven**: Version 3.6.0 or later
- **Git**: For version control

### IDE/Editor

- VS Code with Spring Boot Extensions, or
- IntelliJ IDEA Community/Ultimate Edition

Refer to [PRE_REQUISITES.md](PRE_REQUISITES.md) for detailed setup instructions.

## Getting Started

### 1. Build the Project

```bash
cd task-management-system-api
mvn clean install
```

Or using Maven wrapper:

```bash
./mvnw clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

Or using Maven wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskServiceTest

# Run tests with coverage
mvn test -Dcoverage
```

## API Endpoints

### Base URL

```
http://localhost:8080/tasks
```

### 1. Create Task

**POST** `/tasks`

Create a new task with the provided details.

**Request Body:**

```json
{
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report",
  "dueDate": "2025-12-31",
  "status": "PENDING"
}
```

**Response (201 Created):**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report",
  "status": "PENDING",
  "dueDate": "2025-12-31",
  "createdAt": "2025-05-14T10:30:00",
  "updatedAt": "2025-05-14T10:30:00"
}
```

**Validation:**

- `title` is required (non-blank)
- `dueDate` is required and must be in the future
- `status` defaults to PENDING if not provided

**Error Responses:**

- 400 Bad Request: Missing required fields or invalid date
- 422 Unprocessable Entity: Validation errors

---

### 2. Get Task by ID

**GET** `/tasks/{id}`

Retrieve a specific task by its ID.

**Path Parameters:**

- `id` (string, required): Task unique identifier

**Response (200 OK):**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report",
  "status": "PENDING",
  "dueDate": "2025-12-31",
  "createdAt": "2025-05-14T10:30:00",
  "updatedAt": "2025-05-14T10:30:00"
}
```

**Error Responses:**

- 404 Not Found: Task with given ID does not exist

---

### 3. Update Task

**PUT** `/tasks/{id}`

Update an existing task. All fields are optional.

**Path Parameters:**

- `id` (string, required): Task unique identifier

**Request Body:**

```json
{
  "title": "Updated Task Title",
  "status": "IN_PROGRESS",
  "dueDate": "2025-12-25"
}
```

**Response (200 OK):**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Updated Task Title",
  "description": "Finish the quarterly project report",
  "status": "IN_PROGRESS",
  "dueDate": "2025-12-25",
  "createdAt": "2025-05-14T10:30:00",
  "updatedAt": "2025-05-14T11:00:00"
}
```

**Error Responses:**

- 404 Not Found: Task with given ID does not exist
- 400 Bad Request: Invalid validation (e.g., past due date)

---

### 4. Delete Task

**DELETE** `/tasks/{id}`

Delete a task permanently.

**Path Parameters:**

- `id` (string, required): Task unique identifier

**Response (204 No Content):**

```
Empty body
```

**Error Responses:**

- 404 Not Found: Task with given ID does not exist

---

### 5. Get All Tasks (with Pagination & Filtering)

**GET** `/tasks`

Retrieve all tasks with pagination and optional filtering by status.

**Query Parameters:**

- `page` (integer, optional, default: 0): Page number (0-indexed)
- `pageSize` (integer, optional, default: 10): Number of tasks per page (1-100)
- `status` (enum, optional): Filter by status - PENDING, IN_PROGRESS, or DONE

**Example Requests:**

```
GET /tasks?page=0&pageSize=10                    # Get first 10 tasks
GET /tasks?page=0&pageSize=5&status=PENDING     # Get 5 pending tasks
GET /tasks?page=1&pageSize=20                    # Get tasks on page 2
```

**Response (200 OK):**

```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "title": "Task 1",
      "description": "Description 1",
      "status": "PENDING",
      "dueDate": "2025-12-31",
      "createdAt": "2025-05-14T10:30:00",
      "updatedAt": "2025-05-14T10:30:00"
    },
    {
      "id": "550e8400-e29b-41d4-a716-446655440001",
      "title": "Task 2",
      "description": "Description 2",
      "status": "IN_PROGRESS",
      "dueDate": "2025-12-25",
      "createdAt": "2025-05-14T11:00:00",
      "updatedAt": "2025-05-14T11:00:00"
    }
  ],
  "page": 0,
  "pageSize": 10,
  "totalElements": 25,
  "totalPages": 3,
  "hasNext": true,
  "hasPrevious": false
}
```

**Error Responses:**

- 400 Bad Request: Invalid pagination parameters

---

### 6. Health Check

**GET** `/tasks/health`

Simple endpoint to verify API is running.

**Response (200 OK):**

```
Task Management API is running!
```

## Task Status Enum

Tasks can have the following statuses:

| Status      | Display Name |
| ----------- | ------------ |
| PENDING     | Pending      |
| IN_PROGRESS | In Progress  |
| DONE        | Done         |

## Error Handling

All error responses follow this format:

```json
{
  "status": 404,
  "message": "Task not found with id: 123",
  "error": "Resource Not Found",
  "timestamp": "2025-05-14T10:30:00",
  "path": "/tasks/123"
}
```

### Common HTTP Status Codes

| Status                    | Meaning                                 |
| ------------------------- | --------------------------------------- |
| 200 OK                    | Request succeeded                       |
| 201 Created               | Resource created successfully           |
| 204 No Content            | Request succeeded, no content to return |
| 400 Bad Request           | Invalid request (validation failed)     |
| 404 Not Found             | Resource not found                      |
| 500 Internal Server Error | Server error                            |

## Testing

### Unit Tests

Service layer unit tests using Mockito:

- Test case coverage for all service methods
- Validation testing
- Exception handling tests

**Run:** `mvn test -Dtest=TaskServiceTest`

### Integration Tests

Controller integration tests using MockMvc:

- HTTP endpoint testing
- Request/response validation
- Error response validation

**Run:** `mvn test -Dtest=TaskControllerTest`

## Configuration

### application.properties

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/

# Logging
logging.level.root=INFO
logging.level.com.taskmanager=DEBUG

# Jackson Configuration
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=UTC
```

## Building JAR

Create a production-ready JAR file:

```bash
mvn clean package
```

Run the JAR:

```bash
java -jar target/task-management-system-api-1.0.0.jar
```

## Documentation

For detailed information on:

- **Pre-requisites and setup**: See [PRE_REQUISITES.md](PRE_REQUISITES.md)
- **Architecture details**: See Architecture section above
- **Contributing**: See CONTRIBUTING.md (if available)

## Technology Stack

- **Framework**: Spring Boot 3.5.14
- **Java Version**: 21
- **Build Tool**: Maven 3.6+
- **Testing**: JUnit 5, Mockito
- **Validation**: Jakarta Validation
- **Lombok**: Reducing boilerplate code
- **REST**: Spring Web MVC

## Dependencies

### Core

- `spring-boot-starter-web`: REST API support
- `spring-boot-starter-validation`: Input validation
- `spring-boot-starter-hateoas`: HATEOAS support

### Development

- `spring-boot-devtools`: Hot reload
- `lombok`: Boilerplate reduction

### Testing

- `spring-boot-starter-test`: JUnit 5, Mockito, etc.
- `spring-boot-testcontainers`: Container testing

## Future Enhancements

- Database integration (JPA/Hibernate)
- Authentication and Authorization
- Task categories/tags
- Due date reminders/notifications
- Task history/audit trail
- RESTful API documentation (Swagger/OpenAPI)
- Performance caching
- Advanced filtering and search

## Troubleshooting

### Port Already in Use

```bash
# Change port in application.properties
server.port=8081
```

### Maven Build Issues

```bash
mvn clean install -DskipTests
```

### Java Version Mismatch

Ensure JAVA_HOME environment variable points to Java 21 or later.

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Author

S KAMALESH KUMAR - skamaleshkumar25082000@gmail.com

## Support

For issues or questions, please open an issue on GitHub.

---

**Last Updated**: May 14, 2025
**Version**: 1.0.0
**Status**: Stable
