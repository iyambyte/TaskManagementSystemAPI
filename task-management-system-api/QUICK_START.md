# Quick Start Guide - Task Management System API

## 🚀 Get Started in 5 Minutes

### Step 1: Verify Prerequisites (2 min)

Open PowerShell and verify your setup:

```powershell
java -version
# Output should show Java 21 or later

mvn -version
# Output should show Maven 3.6.0 or later

git --version
# Output should show git version
```

If any of these fail, follow instructions in [PRE_REQUISITES.md](PRE_REQUISITES.md)

---

### Step 2: Build Project (2 min)

```powershell
# Navigate to project directory
cd "c:\Users\KAMAL\OneDrive\Desktop\kamal\Java\task-management-system-api\task-management-system-api"

# Clean and build
mvn clean install
```

✅ **Build successful** if you see `BUILD SUCCESS`

---

### Step 3: Run Application (1 min)

```powershell
mvn spring-boot:run
```

Wait for:

```
Started TaskManagementSystemApiApplication in X.XXX seconds
```

Application is now running at: **http://localhost:8080**

---

### Step 4: Test API Endpoints

Open a new PowerShell window and test:

#### Health Check

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/tasks/health" -Method GET
```

#### Create a Task (Replace date with future date)

```powershell
$body = @{
    title = "My First Task"
    description = "Test task creation"
    dueDate = "2025-12-31"
    status = "PENDING"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/tasks" `
  -Method POST `
  -ContentType "application/json" `
  -Body $body
```

#### Get All Tasks

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/tasks?page=0&pageSize=10" -Method GET
```

---

### Step 5: Push to GitHub

See [GITHUB_SETUP.md](GITHUB_SETUP.md) for complete instructions.

Quick version:

```powershell
# Initialize git
git init

# Add all files
git add .

# Create initial commit
git commit -m "Initial commit: Task Management API"

# Add remote (replace with your GitHub repo URL)
git remote add origin "https://github.com/yourusername/task-management-system-api.git"

# Push to GitHub
git push -u origin main
```

---

## 📚 Complete API Documentation

See [README.md](README.md) for full API documentation with all endpoints.

### Quick Reference

| Method | Endpoint      | Purpose                                      |
| ------ | ------------- | -------------------------------------------- |
| POST   | `/tasks`      | Create task                                  |
| GET    | `/tasks/{id}` | Get task by ID                               |
| PUT    | `/tasks/{id}` | Update task                                  |
| DELETE | `/tasks/{id}` | Delete task                                  |
| GET    | `/tasks`      | List all tasks (with pagination & filtering) |

---

## 🧪 Run Tests

```powershell
# Run all tests
mvn test

# Run only service tests
mvn test -Dtest=TaskServiceTest

# Run only controller tests
mvn test -Dtest=TaskControllerTest

# Run with coverage
mvn test -Dcoverage
```

---

## 📁 Project Structure

```
task-management-system-api/
├── src/main/java/com/taskmanager/task_management_system_api/
│   ├── controller/          ← REST Endpoints
│   ├── service/             ← Business Logic
│   ├── repository/          ← Data Access (In-Memory)
│   ├── entity/              ← Task Entity
│   ├── dto/                 ← Request/Response DTOs
│   ├── enums/               ← TaskStatus
│   ├── exception/           ← Exception Handling
│   └── util/                ← Utilities
├── src/test/java/           ← Unit & Integration Tests
├── README.md                ← Full API Documentation
├── PRE_REQUISITES.md        ← Setup Instructions
├── GITHUB_SETUP.md          ← GitHub Push Guide
└── pom.xml                  ← Maven Configuration
```

---

## 🔧 Configuration

**API Port**: `8080` (change in `src/main/resources/application.properties`)

```properties
server.port=8080
```

---

## 🛠️ Troubleshooting

### Port Already in Use

```powershell
# Change port in application.properties
# server.port=8081

# Or kill process using port 8080
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue).OwningProcess | Stop-Process
```

### Build Errors

```powershell
# Skip tests and build
mvn clean install -DskipTests

# Update Maven cache
mvn clean install -U
```

### Java Version Issues

```powershell
# Check which Java is being used
java -version

# Set JAVA_HOME (PowerShell)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
```

---

## 📝 Key Features

✅ **CRUD Operations** - Create, Read, Update, Delete tasks
✅ **Pagination** - Efficient data retrieval
✅ **Filtering** - Filter tasks by status
✅ **Validation** - Input validation with meaningful errors
✅ **Error Handling** - Consistent error responses
✅ **In-Memory Storage** - No database needed
✅ **Unit Tests** - Comprehensive service tests
✅ **Integration Tests** - Controller endpoint tests
✅ **Clean Architecture** - Modular and maintainable code

---

## 🎯 Next Steps

1. ✅ Build & run the project
2. ✅ Test the API endpoints
3. ✅ Run the test suite
4. ✅ Push to GitHub
5. ✅ Share with colleagues

---

## 📞 Support Resources

- **API Docs**: [README.md](README.md)
- **Setup Guide**: [PRE_REQUISITES.md](PRE_REQUISITES.md)
- **GitHub Guide**: [GITHUB_SETUP.md](GITHUB_SETUP.md)
- **GitHub**: https://github.com
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Maven**: https://maven.apache.org

---

## 💡 Pro Tips

1. **Use REST Client Extension** in VS Code for easier API testing
2. **Configure IDE** with Spring Boot extensions for better development
3. **Create feature branches** when adding new functionality
4. **Write tests** before implementing features (TDD)
5. **Keep commits small** and meaningful

---

**Happy coding! 🚀**

For detailed information on any topic, refer to the corresponding documentation file.

Last Updated: May 14, 2025
