# Task Management API - Complete Testing Scenarios Guide

## Prerequisites

- Application running: `mvn spring-boot:run`
- Base URL: `http://localhost:8080`
- Use Postman or Chrome URL bar for testing

---

## 📋 Test Scenarios Overview

| Category                 | Tests            | Priority |
| ------------------------ | ---------------- | -------- |
| Create Task (Happy Path) | 3                | HIGH     |
| Create Task (Bad Cases)  | 7                | HIGH     |
| Get Task by ID           | 5                | HIGH     |
| Update Task              | 6                | HIGH     |
| Delete Task              | 3                | HIGH     |
| Pagination               | 6                | MEDIUM   |
| Filtering                | 4                | MEDIUM   |
| Edge Cases               | 8                | MEDIUM   |
| **TOTAL**                | **42 Scenarios** | -        |

---

# ✅ HAPPY PATH SCENARIOS

## 1. Create Task (Valid Request)

### Scenario 1.1: Create Task with All Fields

**Method**: POST  
**URL**: `http://localhost:8080/tasks`  
**Content-Type**: `application/json`

**Request Body**:

```json
{
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report with all details",
  "dueDate": "2026-12-31",
  "status": "PENDING"
}
```

**Expected Response** (201 Created):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report with all details",
  "status": "PENDING",
  "dueDate": "2026-12-31",
  "createdAt": "2026-05-14T12:00:00",
  "updatedAt": "2026-05-14T12:00:00"
}
```

**What to Check**:

- ✅ Status code is 201
- ✅ ID is auto-generated (UUID format)
- ✅ Timestamps are set
- ✅ All fields are returned

---

### Scenario 1.2: Create Task with Only Required Fields

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Simple Task",
  "dueDate": "2025-12-25"
}
```

**Expected Response** (201 Created):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Simple Task",
  "description": null,
  "status": "PENDING",
  "dueDate": "2025-12-25",
  "createdAt": "2026-05-14T12:01:00",
  "updatedAt": "2026-05-14T12:01:00"
}
```

**What to Check**:

- ✅ Status is 201
- ✅ Description is null (optional)
- ✅ Status defaults to PENDING

---

### Scenario 1.3: Create Task with Different Status

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "In Progress Task",
  "description": "Already working on this",
  "dueDate": "2025-12-20",
  "status": "IN_PROGRESS"
}
```

**Expected Response** (201 Created):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440002",
  "title": "In Progress Task",
  "description": "Already working on this",
  "status": "IN_PROGRESS",
  "dueDate": "2025-12-20",
  "createdAt": "2026-05-14T12:02:00",
  "updatedAt": "2026-05-14T12:02:00"
}
```

**What to Check**:

- ✅ Status field is correctly set to IN_PROGRESS

---

## 2. Get Task by ID (Happy Path)

### Scenario 2.1: Get Existing Task

**Method**: GET  
**URL**: `http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440000`

**Expected Response** (200 OK):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report with all details",
  "status": "PENDING",
  "dueDate": "2025-12-31",
  "createdAt": "2026-05-14T12:00:00",
  "updatedAt": "2026-05-14T12:00:00"
}
```

**What to Check**:

- ✅ Status code is 200
- ✅ Task data is complete and accurate

---

## 3. Update Task (Happy Path)

### Scenario 3.1: Update Task Status

**Method**: PUT  
**URL**: `http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440000`

**Request Body**:

```json
{
  "status": "IN_PROGRESS"
}
```

**Expected Response** (200 OK):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Complete Project Report",
  "description": "Finish the quarterly project report with all details",
  "status": "IN_PROGRESS",
  "dueDate": "2025-12-31",
  "createdAt": "2026-05-14T12:00:00",
  "updatedAt": "2026-05-14T12:03:00"
}
```

**What to Check**:

- ✅ Status is updated
- ✅ UpdatedAt timestamp changed
- ✅ Other fields unchanged

---

### Scenario 3.2: Update Task Description

**Method**: PUT  
**URL**: `http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440001`

**Request Body**:

```json
{
  "description": "Updated description with more details"
}
```

**Expected Response** (200 OK):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Simple Task",
  "description": "Updated description with more details",
  "status": "PENDING",
  "dueDate": "2025-12-25",
  "createdAt": "2026-05-14T12:01:00",
  "updatedAt": "2026-05-14T12:04:00"
}
```

**What to Check**:

- ✅ Description updated
- ✅ Other fields preserved

---

### Scenario 3.3: Update Multiple Fields

**Method**: PUT  
**URL**: `http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440002`

**Request Body**:

```json
{
  "title": "Updated Title",
  "status": "DONE",
  "dueDate": "2025-12-15"
}
```

**Expected Response** (200 OK):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440002",
  "title": "Updated Title",
  "description": "Already working on this",
  "status": "DONE",
  "dueDate": "2025-12-15",
  "createdAt": "2026-05-14T12:02:00",
  "updatedAt": "2026-05-14T12:05:00"
}
```

**What to Check**:

- ✅ Multiple fields updated
- ✅ Partial update works

---

## 4. Delete Task (Happy Path)

### Scenario 4.1: Delete Existing Task

**Method**: DELETE  
**URL**: `http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440000`

**Expected Response** (204 No Content):

```
[Empty body]
```

**What to Check**:

- ✅ Status code is 204
- ✅ No response body

---

## 5. List All Tasks (Happy Path)

### Scenario 5.1: Get All Tasks

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=10`

**Expected Response** (200 OK):

```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440001",
      "title": "Simple Task",
      "description": null,
      "status": "PENDING",
      "dueDate": "2025-12-25",
      "createdAt": "2026-05-14T12:01:00",
      "updatedAt": "2026-05-14T12:01:00"
    }
  ],
  "page": 0,
  "pageSize": 10,
  "totalElements": 1,
  "totalPages": 1,
  "hasNext": false,
  "hasPrevious": false
}
```

**What to Check**:

- ✅ Status code is 200
- ✅ Tasks sorted by due date
- ✅ Pagination metadata correct

---

# ❌ BAD CASES / ERROR SCENARIOS

## 6. Create Task - Missing Required Fields

### Scenario 6.1: Missing Title

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "description": "No title provided",
  "dueDate": "2025-12-31"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "title: Title is required",
  "error": "Validation Failed",
  "timestamp": "2026-05-14T12:06:00",
  "path": "/tasks"
}
```

**What to Check**:

- ✅ Status code is 400
- ✅ Error message mentions "title"
- ✅ Error type is "Validation Failed"

---

### Scenario 6.2: Missing Due Date

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Task without date"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "dueDate: Due date is required",
  "error": "Validation Failed",
  "timestamp": "2026-05-14T12:07:00",
  "path": "/tasks"
}
```

**What to Check**:

- ✅ Status code is 400
- ✅ Error mentions "dueDate"

---

### Scenario 6.3: Blank Title (Empty String)

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "   ",
  "dueDate": "2025-12-31"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "title: Title is required",
  "error": "Validation Failed",
  "timestamp": "2026-05-14T12:08:00",
  "path": "/tasks"
}
```

**What to Check**:

- ✅ Whitespace-only title rejected

---

## 7. Create Task - Invalid Due Date

### Scenario 7.1: Past Due Date

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Past Task",
  "dueDate": "2020-12-31"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Due date must be in the future",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:09:00",
  "path": "/tasks"
}
```

**What to Check**:

- ✅ Status code is 400
- ✅ Error message mentions "future"

---

### Scenario 7.2: Today's Date (Not Future)

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Task Due Today",
  "dueDate": "2026-05-14"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Due date must be in the future",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:10:00",
  "path": "/tasks"
}
```

**What to Check**:

- ✅ Today's date is rejected (must be strictly future)

---

### Scenario 7.3: Invalid Date Format

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Invalid Date Task",
  "dueDate": "31-12-2025"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Invalid JSON",
  "error": "Bad Request",
  "timestamp": "2026-05-14T12:11:00",
  "path": "/tasks"
}
```

**What to Check**:

- ✅ Invalid date format rejected
- ✅ Standard JSON parsing error

---

## 8. Get Task - Not Found

### Scenario 8.1: Non-Existent Task ID

**Method**: GET  
**URL**: `http://localhost:8080/tasks/00000000-0000-0000-0000-000000000000`

**Expected Response** (404 Not Found):

```json
{
  "status": 404,
  "message": "Task not found with id: 00000000-0000-0000-0000-000000000000",
  "error": "Resource Not Found",
  "timestamp": "2026-05-14T12:12:00",
  "path": "/tasks/00000000-0000-0000-0000-000000000000"
}
```

**What to Check**:

- ✅ Status code is 404
- ✅ Error type is "Resource Not Found"
- ✅ ID shown in message

---

### Scenario 8.2: Invalid UUID Format

**Method**: GET  
**URL**: `http://localhost:8080/tasks/invalid-id-format`

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Task ID cannot be null or empty",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:13:00",
  "path": "/tasks/invalid-id-format"
}
```

**What to Check**:

- ✅ Validation fails on invalid format

---

### Scenario 8.3: Empty ID in URL

**Method**: GET  
**URL**: `http://localhost:8080/tasks/`

**Expected Response** (404 Not Found):

```
404 - Page not found (Spring routing error)
```

**What to Check**:

- ✅ Spring router handles empty path

---

## 9. Update Task - Not Found

### Scenario 9.1: Update Non-Existent Task

**Method**: PUT  
**URL**: `http://localhost:8080/tasks/00000000-0000-0000-0000-000000000000`

**Request Body**:

```json
{
  "title": "Updated Title"
}
```

**Expected Response** (404 Not Found):

```json
{
  "status": 404,
  "message": "Task not found with id: 00000000-0000-0000-0000-000000000000",
  "error": "Resource Not Found",
  "timestamp": "2026-05-14T12:14:00",
  "path": "/tasks/00000000-0000-0000-0000-000000000000"
}
```

**What to Check**:

- ✅ Status code is 404

---

### Scenario 9.2: Update with Invalid Future Date

**Method**: PUT  
**URL**: `http://localhost:8080/tasks/550e8400-e29b-41d4-a716-446655440001`

**Request Body**:

```json
{
  "dueDate": "2020-01-01"
}
```

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Due date must be in the future",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:15:00",
  "path": "/tasks/550e8400-e29b-41d4-a716-446655440001"
}
```

**What to Check**:

- ✅ Validation on update

---

## 10. Delete Task - Not Found

### Scenario 10.1: Delete Non-Existent Task

**Method**: DELETE  
**URL**: `http://localhost:8080/tasks/00000000-0000-0000-0000-000000000000`

**Expected Response** (404 Not Found):

```json
{
  "status": 404,
  "message": "Task not found with id: 00000000-0000-0000-0000-000000000000",
  "error": "Resource Not Found",
  "timestamp": "2026-05-14T12:16:00",
  "path": "/tasks/00000000-0000-0000-0000-000000000000"
}
```

**What to Check**:

- ✅ Status code is 404
- ✅ Appropriate error message

---

# 📊 PAGINATION SCENARIOS

First, create multiple tasks with different dates to test pagination:

```json
{
  "title": "Task 1",
  "dueDate": "2025-12-25",
  "status": "PENDING"
}
```

```json
{
  "title": "Task 2",
  "dueDate": "2025-12-26",
  "status": "PENDING"
}
```

```json
{
  "title": "Task 3",
  "dueDate": "2025-12-27",
  "status": "IN_PROGRESS"
}
```

(Create at least 15-20 tasks for proper pagination testing)

---

## 11. Pagination - Page Size Variations

### Scenario 11.1: Page Size 5

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=5`

**Expected Response** (200 OK):

```json
{
  "content": [
    // 5 tasks sorted by due date
  ],
  "page": 0,
  "pageSize": 5,
  "totalElements": 20,
  "totalPages": 4,
  "hasNext": true,
  "hasPrevious": false
}
```

**What to Check**:

- ✅ Exactly 5 tasks returned
- ✅ hasNext is true
- ✅ hasPrevious is false

---

### Scenario 11.2: Different Page (Page 1)

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=1&pageSize=5`

**Expected Response** (200 OK):

```json
{
  "content": [
    // Next 5 tasks
  ],
  "page": 1,
  "pageSize": 5,
  "totalElements": 20,
  "totalPages": 4,
  "hasNext": true,
  "hasPrevious": true
}
```

**What to Check**:

- ✅ Different tasks on page 1
- ✅ hasPrevious is true
- ✅ hasPrevious is true

---

### Scenario 11.3: Last Page

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=3&pageSize=5`

**Expected Response** (200 OK):

```json
{
  "content": [
    // Remaining tasks (0-5 tasks)
  ],
  "page": 3,
  "pageSize": 5,
  "totalElements": 20,
  "totalPages": 4,
  "hasNext": false,
  "hasPrevious": true
}
```

**What to Check**:

- ✅ hasNext is false
- ✅ hasPrevious is true

---

### Scenario 11.4: Maximum Page Size (100)

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=100`

**Expected Response** (200 OK):

```json
{
  "content": [
    // All tasks (max 100)
  ],
  "page": 0,
  "pageSize": 100,
  "totalElements": 20,
  "totalPages": 1,
  "hasNext": false,
  "hasPrevious": false
}
```

**What to Check**:

- ✅ All 20 tasks returned
- ✅ One page total

---

## 12. Pagination - Invalid Cases

### Scenario 12.1: Negative Page Number

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=-1&pageSize=10`

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Page number cannot be negative",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:17:00",
  "path": "/tasks?page=-1&pageSize=10"
}
```

**What to Check**:

- ✅ Status code is 400
- ✅ Error message about page number

---

### Scenario 12.2: Page Size = 0

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=0`

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Page size must be between 1 and 100",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:18:00",
  "path": "/tasks?page=0&pageSize=0"
}
```

**What to Check**:

- ✅ Status code is 400
- ✅ Minimum page size enforced

---

### Scenario 12.3: Page Size > 100

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=150`

**Expected Response** (400 Bad Request):

```json
{
  "status": 400,
  "message": "Page size must be between 1 and 100",
  "error": "Validation Error",
  "timestamp": "2026-05-14T12:19:00",
  "path": "/tasks?page=0&pageSize=150"
}
```

**What to Check**:

- ✅ Maximum page size enforced

---

### Scenario 12.4: Beyond Available Pages

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=10&pageSize=5`

**Expected Response** (200 OK):

```json
{
  "content": [],
  "page": 10,
  "pageSize": 5,
  "totalElements": 20,
  "totalPages": 4,
  "hasNext": false,
  "hasPrevious": true
}
```

**What to Check**:

- ✅ Empty content array
- ✅ No error (graceful handling)

---

# 🔍 FILTERING SCENARIOS

## 13. Filter by Status - PENDING

### Scenario 13.1: Get All PENDING Tasks

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=10&status=PENDING`

**Expected Response** (200 OK):

```json
{
  "content": [
    {
      "id": "...",
      "title": "Task 1",
      "status": "PENDING",
      "dueDate": "2025-12-25",
      ...
    },
    {
      "id": "...",
      "title": "Task 2",
      "status": "PENDING",
      "dueDate": "2025-12-26",
      ...
    }
  ],
  "page": 0,
  "pageSize": 10,
  "totalElements": 12,
  "totalPages": 2,
  "hasNext": true,
  "hasPrevious": false
}
```

**What to Check**:

- ✅ Only PENDING tasks returned
- ✅ Sorted by due date
- ✅ Total elements shows filtered count

---

## 14. Filter by Status - IN_PROGRESS

### Scenario 14.1: Get All IN_PROGRESS Tasks

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=10&status=IN_PROGRESS`

**Expected Response** (200 OK):

```json
{
  "content": [
    {
      "status": "IN_PROGRESS",
      ...
    }
  ],
  "totalElements": 5,
  ...
}
```

**What to Check**:

- ✅ Only IN_PROGRESS tasks returned

---

## 15. Filter by Status - DONE

### Scenario 15.1: Get All DONE Tasks

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=10&status=DONE`

**Expected Response** (200 OK):

```json
{
  "content": [
    {
      "status": "DONE",
      ...
    }
  ],
  "totalElements": 3,
  ...
}
```

**What to Check**:

- ✅ Only DONE tasks returned

---

## 16. Filtering + Pagination Combined

### Scenario 16.1: Filter PENDING with Pagination

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=0&pageSize=5&status=PENDING`

**Expected Response** (200 OK):

```json
{
  "content": [
    // 5 PENDING tasks (if available)
  ],
  "page": 0,
  "pageSize": 5,
  "totalElements": 12, // Total PENDING tasks
  "totalPages": 3, // Pages of PENDING tasks
  "hasNext": true,
  "hasPrevious": false
}
```

**What to Check**:

- ✅ Filters applied
- ✅ Pagination works on filtered results
- ✅ Total elements is for filtered set

---

### Scenario 16.2: Second Page of Filtered Results

**Method**: GET  
**URL**: `http://localhost:8080/tasks?page=1&pageSize=5&status=IN_PROGRESS`

**Expected Response** (200 OK):

```json
{
  "content": [
    // Next 5 IN_PROGRESS tasks
  ],
  "page": 1,
  "pageSize": 5,
  "totalElements": 5,
  "totalPages": 1,
  "hasNext": false,
  "hasPrevious": true
}
```

**What to Check**:

- ✅ Pagination within filtered results

---

# 🎯 EDGE CASES & STRESS SCENARIOS

## 17. Special Characters in Task Title

### Scenario 17.1: Title with Special Characters

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Task with @#$%^&*() symbols!",
  "dueDate": "2025-12-31"
}
```

**Expected Response** (201 Created):

```json
{
  "id": "...",
  "title": "Task with @#$%^&*() symbols!",
  ...
}
```

**What to Check**:

- ✅ Special characters preserved
- ✅ Task created successfully

---

## 18. Long Text Fields

### Scenario 18.1: Very Long Title

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
  "dueDate": "2025-12-31"
}
```

**Expected Response** (201 Created):

```json
{
  "id": "...",
  "title": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
  ...
}
```

**What to Check**:

- ✅ Long text handled
- ✅ No truncation

---

### Scenario 18.2: Very Long Description

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Long Description Task",
  "description": "A".repeat(5000),
  "dueDate": "2025-12-31"
}
```

**Expected Response** (201 Created):

- ✅ Handles large descriptions

---

## 19. Empty vs Null Fields

### Scenario 19.1: Null Description

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Task with null description",
  "description": null,
  "dueDate": "2025-12-31"
}
```

**Expected Response** (201 Created):

```json
{
  "description": null,
  ...
}
```

**What to Check**:

- ✅ Null is accepted for optional fields

---

### Scenario 19.2: Empty String Description

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Task with empty description",
  "description": "",
  "dueDate": "2025-12-31"
}
```

**Expected Response** (201 Created):

```json
{
  "description": "",
  ...
}
```

**What to Check**:

- ✅ Empty string is accepted

---

## 20. Rapid Create-Read-Update-Delete Cycle

### Scenario 20.1: CRUD Sequence

1. **Create** task → Get ID
2. **Get** task by ID
3. **Update** task
4. **Get** updated task
5. **Delete** task
6. **Get** deleted task (should 404)

**What to Check**:

- ✅ All operations work in sequence
- ✅ ID persists through cycle
- ✅ Updates reflected in retrieval
- ✅ Delete removes task completely

---

## 21. Concurrent Status Transitions

### Scenario 21.1: Update Status Multiple Times

**Method**: PUT repeatedly on same task

**Sequence**:

```
PENDING → IN_PROGRESS → DONE → IN_PROGRESS → PENDING
```

**What to Check**:

- ✅ Status updates each time
- ✅ No conflicts
- ✅ Final status is last update

---

## 22. Boundary Dates

### Scenario 22.1: Far Future Date

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Far future task",
  "dueDate": "2099-12-31"
}
```

**Expected Response** (201 Created):

- ✅ Accepted without issue

---

### Scenario 22.2: One Day in Future

**Method**: POST  
**URL**: `http://localhost:8080/tasks`

**Request Body**:

```json
{
  "title": "Tomorrow task",
  "dueDate": "2026-05-15"
}
```

**Expected Response** (201 Created):

- ✅ Minimum future date accepted

---

## 23. Duplicate Tasks

### Scenario 23.1: Create Same Task Twice

**Method**: POST twice with same data

**Request Body**:

```json
{
  "title": "Duplicate Task",
  "dueDate": "2025-12-31"
}
```

**Expected Response** (Both 201):

```json
{
  "id": "different-uuid-1",
  ...
}
```

```json
{
  "id": "different-uuid-2",
  ...
}
```

**What to Check**:

- ✅ Each task gets unique ID
- ✅ No uniqueness constraint on content

---

## 24. Health Check

### Scenario 24.1: Health Check Endpoint

**Method**: GET  
**URL**: `http://localhost:8080/tasks/health`

**Expected Response** (200 OK):

```
Task Management API is running!
```

**What to Check**:

- ✅ API is alive and responding
- ✅ Standard health check works

---

# 📝 TESTING CHECKLIST

## Before Testing

- [ ] Application is running: `mvn spring-boot:run`
- [ ] Application is accessible: `http://localhost:8080/tasks/health`
- [ ] Database/In-memory store is initialized

## Create Task Tests

- [ ] Happy path (all fields)
- [ ] Happy path (minimal fields)
- [ ] Happy path (different status)
- [ ] Missing title
- [ ] Missing due date
- [ ] Blank title
- [ ] Past due date
- [ ] Today's due date
- [ ] Invalid date format

## Get Task Tests

- [ ] Existing task
- [ ] Non-existent task
- [ ] Invalid UUID

## Update Task Tests

- [ ] Update status
- [ ] Update description
- [ ] Update multiple fields
- [ ] Update non-existent task
- [ ] Update with past date

## Delete Task Tests

- [ ] Delete existing task
- [ ] Delete non-existent task
- [ ] Verify task is gone (404)

## Pagination Tests

- [ ] Page size 5
- [ ] Different pages
- [ ] Last page
- [ ] Max page size (100)
- [ ] Negative page
- [ ] Page size 0
- [ ] Page size > 100
- [ ] Beyond available pages

## Filtering Tests

- [ ] Filter PENDING
- [ ] Filter IN_PROGRESS
- [ ] Filter DONE
- [ ] Filter + pagination combined

## Edge Cases

- [ ] Special characters
- [ ] Long title
- [ ] Long description
- [ ] Null description
- [ ] Empty description
- [ ] Far future date
- [ ] One day future
- [ ] Duplicate tasks

---

# 🚀 Quick Test Commands for Chrome/Postman

## Using Chrome URL Bar (GET requests only)

```
http://localhost:8080/tasks
http://localhost:8080/tasks?page=0&pageSize=10
http://localhost:8080/tasks?page=0&pageSize=10&status=PENDING
http://localhost:8080/tasks/health
```

## Using Postman

1. Create Collection: "Task Management API"
2. Create requests for each scenario
3. Use variables for base URL: `{{base_url}}`
4. Set base_url to: `http://localhost:8080`

---

**Total Test Scenarios: 42+**  
**Estimated Testing Time: 1-2 hours**  
**Priority: Complete happy paths first, then error cases, then edge cases**

Happy Testing! 🎉
