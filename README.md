# TaskFlow Backend

A comprehensive task management system backend built with Spring Boot, featuring role-based access control, JWT authentication, and PostgreSQL database integration.

## 🚀 Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **User Management**: Registration, login, profile management for Developers and Managers
- **Task Management**: CRUD operations for tasks with state management
- **Team Management**: Assign tasks to developers, manage team compositions
- **Database Migration**: Flyway for database schema management
- **Security**: Spring Security with OAuth2 Resource Server
- **API Documentation**: Comprehensive REST API endpoints
- **Testing**: Comprehensive unit tests for controllers, services, and mappers

## 🛠 Technology Stack

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Security** with OAuth2 Resource Server
- **Spring Data JPA** with Hibernate
- **PostgreSQL** Database
- **Flyway** for database migrations
- **JWT** for authentication
- **MapStruct** for object mapping
- **Lombok** for reducing boilerplate code
- **Maven** for dependency management
- **Docker** & **Docker Compose** for containerization

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose
- PostgreSQL

## 🏗 Project Structure

```
src/
├── main/java/os/org/taskflow/
│   ├── auth/           # Authentication and user management
│   ├── common/         # Common utilities and response entities
│   ├── constant/       # Application constants
│   ├── developer/      # Developer entity and management
│   ├── manager/        # Manager entity and team management
│   ├── security/       # Security configuration and JWT handling
│   ├── task/           # Task management
│   └── TaskflowApplication.java
├── main/resources/
│   ├── application.yaml
│   ├── application-dev.yaml
│   └── db/migration/   # Flyway migration scripts
└── test/               # Unit tests
```

## 🚀 Quick Start with Docker

### 1. Clone the Repository
```bash
git clone https://github.com/soufianebouaddis/TaskFlow-BackEnd.git
cd taskflow
```

### 2. Run with Docker Compose
```bash
# Run in detached mode
docker-compose up -d 
```

### 3. Access the Application
- **Application**: http://localhost:8880
- **PostgreSQL**: localhost:5423
- **Database**: taskflow_dev

## 🔧 Local Development Setup

### 1. Database Setup
```bash
# Create PostgreSQL database
createdb taskflow_dev

# Or using Docker
docker run --name taskflow_postgres \
  -e POSTGRES_DB=taskflow_dev \
  -e POSTGRES_USER=root \
  -e POSTGRES_PASSWORD=root \
  -p 5432:5432 \
  -d postgres:16-alpine
```

### 2. Application Configuration
The application uses the following default configuration:
- **Port**: 8880
- **Database**: PostgreSQL on localhost:5432
- **Profile**: dev (active by default)

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or build and run
mvn clean package
java -jar target/taskflow-0.0.1-SNAPSHOT.jar
```

## 📚 API Endpoints

### Authentication (`/api/v1/auth/`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/register` | Register new user | Public |
| POST | `/login` | User login | Public |
| GET | `/profile` | Get user profile | Authenticated |
| PUT | `/{id}` | Update user profile | Authenticated |
| POST | `/logout` | User logout | Authenticated |

### Tasks (`/api/v1/tasks/`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/` | Create new task | MANAGER |
| GET | `/` | Get all tasks | MANAGER, DEVELOPER |
| PUT | `/{id}` | Update task | MANAGER, DEVELOPER |
| DELETE | `/{id}` | Delete task | MANAGER |

### Developers (`/api/v1/developers/`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/` | Get all developers | Authenticated |

### Manager Operations (`/api/v1/manager/`)
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/{taskId}/{developerId}` | Assign task to developer | MANAGER |
| POST | `/team/{managerId}/{developerId}` | Add developer to team | MANAGER |

## 🔐 Security

### JWT Authentication
- Uses RSA key pairs for JWT signing
- Tokens are stored in HTTP-only cookies
- CSRF protection enabled
- Role-based authorization with `@PreAuthorize`

### User Roles
- **DEVELOPER**: Can view tasks, update assigned tasks
- **MANAGER**: Can create, delete tasks, assign tasks to developers, manage teams

## 🗄 Database

### Migration
The application uses Flyway for database migrations:
```bash
# Run migrations manually
mvn flyway:migrate

# Clean migrations
mvn flyway:clean
```

### Schema
- **Users**: Authentication and user management
- **Tasks**: Task information and state management
- **Developers**: Developer profiles and task assignments
- **Managers**: Manager profiles and team management

## 🧪 Testing

### Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskControllerTest
```

### Test Coverage
- **Controllers**: All REST endpoints tested
- **Services**: Business logic validation
- **Mappers**: Object mapping verification
- **Entities**: Data validation and relationships

## 🐳 Docker Configuration

### Dockerfile
- Multi-stage build for optimized image size
- Alpine-based JDK for security and size
- Non-root user execution for security
- Exposed port 8880

### Docker Compose
- PostgreSQL service on port 5423
- Application service on port 8880
- Persistent volume for database data
- Health checks and dependency management


### Profiles
- **dev**: Development configuration with debug logging
- **prod**: Production configuration (create as needed)

## 📝 Usage Examples

### Register a New User
```bash
curl -X POST http://localhost:8880/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "password123",
    "role": "DEVELOPER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8880/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```

### Create a Task (Manager only)
```bash
curl -X POST http://localhost:8880/api/v1/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt-token>" \
  -d '{
    "taskLabel": "Implement User Authentication",
    "taskState": "TODO"
  }'
```