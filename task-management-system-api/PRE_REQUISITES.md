# Task Management System API - Pre-Requisites & Setup Guide

## Pre-Requisites (Local Environment Check)

### 1. Java Development Kit (JDK)

- **Required Version**: Java 21 or later
- **Verify Installation**:
  ```bash
  java -version
  javac -version
  ```
- **Location**: Ensure JAVA_HOME environment variable is set correctly
  - Windows: `C:\Program Files\Java\jdk-21` (or your installation path)
  - Add to PATH: `%JAVA_HOME%\bin`

### 2. Apache Maven

- **Required Version**: Maven 3.6.0 or later
- **Verify Installation**:
  ```bash
  mvn -version
  ```
- **Note**: Spring Boot 3.5.14 uses Maven for build management
- **Location**: Ensure MAVEN_HOME environment variable is set

### 3. Git

- **Required for**: Version control and GitHub integration
- **Verify Installation**:
  ```bash
  git --version
  ```
- **Configuration**:
  ```bash
  git config --global user.name "Your Name"
  git config --global user.email "your.email@example.com"
  ```

### 4. IDE/Editor

- **Recommended**: VS Code with Spring Boot Extensions or IntelliJ IDEA
- **VS Code Extensions to install**:
  - Extension Pack for Java
  - Spring Boot Extension Pack
  - REST Client (for testing APIs)

### 5. Build Tools

- **Maven Wrapper**: Included in project (`mvnw` and `mvnw.cmd`)
- **Use**: `./mvnw` on Linux/Mac or `mvnw.cmd` on Windows

---

## Dependencies Overview

### Core Dependencies

```
1. spring-boot-starter-web
   - Purpose: REST API development, embedded Tomcat

2. spring-boot-starter-validation
   - Purpose: Input validation using @Valid, @NotNull, etc.

3. spring-boot-starter-hateoas
   - Purpose: HATEOAS links in responses (optional but good practice)

4. lombok
   - Purpose: Reduce boilerplate code with @Data, @Getter, @Setter
```

### Testing Dependencies

```
1. spring-boot-starter-test
   - Includes: JUnit 5, Mockito, AssertJ, Spring Test

2. spring-boot-testcontainers
   - Purpose: Container-based testing (if needed)
```

### Additional Dependencies (Already included)

```
- spring-boot-starter-data-jpa (keep for future database migration)
- spring-boot-devtools (for development hot reload)
```

---

## Project Structure

```
task-management-system-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── taskmanager/
│   │   │           └── taskmanagementapi/
│   │   │               ├── TaskManagementSystemApiApplication.java
│   │   │               ├── config/
│   │   │               │   └── AppConfig.java
│   │   │               ├── controller/
│   │   │               │   └── TaskController.java
│   │   │               ├── service/
│   │   │               │   ├── TaskService.java
│   │   │               │   └── TaskServiceImpl.java
│   │   │               ├── repository/
│   │   │               │   └── TaskRepository.java
│   │   │               ├── entity/
│   │   │               │   └── Task.java
│   │   │               ├── dto/
│   │   │               │   ├── TaskDTO.java
│   │   │               │   ├── TaskCreateRequest.java
│   │   │               │   └── TaskUpdateRequest.java
│   │   │               ├── enums/
│   │   │               │   └── TaskStatus.java
│   │   │               ├── exception/
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── ResourceNotFoundException.java
│   │   │               │   └── ValidationException.java
│   │   │               └── util/
│   │   │                   └── ValidationUtil.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── taskmanager/
│                   └── taskmanagementapi/
│                       ├── service/
│                       │   └── TaskServiceTest.java
│                       └── controller/
│                           └── TaskControllerTest.java
├── pom.xml
├── PRE_REQUISITES.md
├── README.md
└── .gitignore
```

---

## Quick Start Commands

### 1. Verify Environment Setup

```bash
java -version
mvn -version
git --version
```

### 2. Build Project

```bash
cd task-management-system-api
mvn clean install
# or using wrapper
./mvnw clean install
```

### 3. Run Application

```bash
mvn spring-boot:run
# or
./mvnw spring-boot:run
```

### 4. Run Tests

```bash
mvn test
# or
./mvnw test
```

### 5. Build JAR

```bash
mvn clean package
# or
./mvnw clean package
```

---

## Port Configuration

- **Default Port**: 8080
- **Configure**: Edit `src/main/resources/application.properties`
  ```properties
  server.port=8080
  ```

---

## API Base URL (Once Running)

```
http://localhost:8080/tasks
```

---

## Troubleshooting

### Maven Build Issues

```bash
mvn clean install -DskipTests
# If using maven wrapper
./mvnw clean install -DskipTests
```

### Java Version Mismatch

- Ensure JAVA_HOME points to Java 21 or later
- Update system PATH

### Port Already in Use

- Change server.port in application.properties
- Or kill existing process using port 8080

---

## Next Steps

1. ✅ Verify all pre-requisites
2. Build the project with `mvn clean install`
3. Create package structure
4. Implement entities and DTOs
5. Create repository and service layers
6. Build REST controllers
7. Add comprehensive tests
8. Push to GitHub
