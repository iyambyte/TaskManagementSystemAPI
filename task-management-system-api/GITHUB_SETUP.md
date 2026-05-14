# GitHub Setup & Deployment Guide

## Step-by-Step Guide to Push Your Project to GitHub

### Prerequisites

- GitHub account (create one at https://github.com if you don't have it)
- Git installed locally
- Project completed and ready

---

## Part 1: Initial Git Setup (One-time)

### 1. Configure Git (First time only)

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

Verify configuration:

```bash
git config --global --list
```

---

## Part 2: Create GitHub Repository

### 1. Create Repository on GitHub

1. Go to https://github.com/new
2. Enter repository name: `task-management-system-api`
3. Add description: "REST API for task management with CRUD operations"
4. Choose visibility:
   - Public (if you want to share with colleagues easily)
   - Private (if you want restricted access)
5. **Do NOT** initialize with README (we already have one)
6. Click "Create repository"

### 2. Copy Repository URL

After creating, copy the repository URL:

- **HTTPS**: `https://github.com/yourusername/task-management-system-api.git`
- **SSH**: `git@github.com:yourusername/task-management-system-api.git`

---

## Part 3: Initialize Local Repository

### 1. Navigate to Project Directory

```bash
cd c:\Users\KAMAL\OneDrive\Desktop\kamal\Java\task-management-system-api\task-management-system-api
```

### 2. Initialize Git Repository

```bash
git init
```

### 3. Add All Files

```bash
git add .
```

Verify staged files:

```bash
git status
```

### 4. Create Initial Commit

```bash
git commit -m "Initial commit: Task Management API with CRUD operations, validation, and tests"
```

---

## Part 4: Connect to Remote and Push

### 1. Add Remote Repository

Using HTTPS (easier):

```bash
git remote add origin https://github.com/yourusername/task-management-system-api.git
```

Using SSH (requires SSH key setup):

```bash
git remote add origin git@github.com:yourusername/task-management-system-api.git
```

### 2. Verify Remote

```bash
git remote -v
```

Should show:

```
origin  https://github.com/yourusername/task-management-system-api.git (fetch)
origin  https://github.com/yourusername/task-management-system-api.git (push)
```

### 3. Rename Branch to Main (if needed)

```bash
git branch -M main
```

### 4. Push to GitHub

```bash
git push -u origin main
```

**Note**: On first push, you may be prompted for GitHub credentials. Enter your GitHub username and password (or personal access token).

---

## Part 5: Verify on GitHub

1. Go to your GitHub repository URL
2. Verify all files are there
3. Check that README.md is displayed properly
4. View the commit history

---

## How to Share with Colleagues

### Option 1: Public Repository (Easiest)

Simply share the GitHub URL:

```
https://github.com/yourusername/task-management-system-api
```

### Option 2: Private Repository with Permissions

1. Go to Settings → Collaborators → Add people
2. Search for colleague's GitHub username
3. Send them the invite link
4. They accept and get access

### For Colleagues to Clone

```bash
git clone https://github.com/yourusername/task-management-system-api.git
cd task-management-system-api
mvn clean install
mvn spring-boot:run
```

---

## Future Updates (After Initial Push)

When you make changes and want to push:

### 1. Check Status

```bash
git status
```

### 2. Stage Changes

```bash
git add .
```

Or specific files:

```bash
git add src/main/java/...
```

### 3. Commit Changes

```bash
git commit -m "Your commit message describing changes"
```

### 4. Push to GitHub

```bash
git push origin main
```

---

## Common Git Commands Reference

```bash
# View status
git status

# Add all files
git add .

# Add specific file
git add filename

# Commit
git commit -m "message"

# Push to remote
git push origin main

# Pull latest changes
git pull origin main

# View commit history
git log --oneline

# View branch information
git branch -a

# Create new branch
git checkout -b feature/new-feature

# Switch branch
git checkout main

# Merge branch
git merge feature/new-feature

# Delete branch
git branch -d branch-name

# View remote info
git remote -v

# Remove file from tracking
git rm --cached filename
```

---

## Troubleshooting

### 1. Authentication Issues (HTTPS)

If you get authentication errors:

- Use GitHub Personal Access Token instead of password
- Create token: https://github.com/settings/tokens
- Settings → Developer settings → Personal access tokens

### 2. SSH Key Setup

If using SSH and need to set up:

```bash
# Generate SSH key
ssh-keygen -t ed25519 -C "your.email@example.com"

# Add to ssh-agent (Windows PowerShell as Admin)
ssh-agent
ssh-add ~/.ssh/id_ed25519

# Copy public key to GitHub
# Copy content of ~/.ssh/id_ed25519.pub to:
# GitHub Settings → SSH and GPG keys → New SSH key
```

### 3. Remote Already Exists Error

```bash
git remote remove origin
git remote add origin <new-url>
```

### 4. Push Rejected

If your local changes are behind:

```bash
git pull origin main
git push origin main
```

---

## Best Practices

1. **Meaningful Commit Messages**: Describe what changed and why
2. **Frequent Commits**: Commit logical units of work
3. **Before Pushing**: Always verify changes with `git status`
4. **Create Branches**: For new features, use feature branches
5. **Pull Before Push**: Always sync with remote before pushing

Example commit messages:

```
"Add validation for future dates"
"Fix pagination calculation in getAllTasks"
"Refactor exception handling to use global handler"
"Add comprehensive unit tests for TaskService"
"Update README with API documentation"
```

---

## Branch Naming Convention (Optional but Recommended)

```
main                      # Production-ready code
develop                   # Integration branch
feature/task-filtering    # New features
bugfix/pagination-issue   # Bug fixes
hotfix/critical-error     # Critical production fixes
```

---

## Sharing Specific Files or Branches with Colleagues

### 1. Create Feature Branch for Colleague Review

```bash
git checkout -b feature/review-me
git push origin feature/review-me
```

### 2. Create Pull Request (PR) on GitHub

1. Go to your repository
2. Click "Pull requests"
3. Click "New pull request"
4. Select branches to compare
5. Add description
6. Request reviewers
7. Click "Create pull request"

---

## Automate with GitHub Actions (Optional)

Add CI/CD pipeline to automatically run tests:

Create `.github/workflows/maven.yml`:

```yaml
name: Java CI with Maven

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 21
        uses: actions/setup-java@v2
        with:
          java-version: "21"
      - name: Build and Test
        run: mvn clean install
```

---

## Quick Reference: First Time Setup

```bash
# 1. Navigate to project
cd task-management-system-api

# 2. Initialize git
git init

# 3. Configure git (first time only)
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# 4. Add all files
git add .

# 5. Create first commit
git commit -m "Initial commit: Task Management API implementation"

# 6. Add remote (replace URL with your GitHub URL)
git remote add origin https://github.com/yourusername/task-management-system-api.git

# 7. Rename branch to main
git branch -M main

# 8. Push to GitHub
git push -u origin main
```

---

## Support & Resources

- GitHub Docs: https://docs.github.com
- Git Documentation: https://git-scm.com/doc
- GitHub CLI: https://cli.github.com
- Git Cheat Sheet: https://education.github.com/git-cheat-sheet-education.pdf

---

**Note**: Replace `yourusername` with your actual GitHub username in all URLs.

Last Updated: May 14, 2025
