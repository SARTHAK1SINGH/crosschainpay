# User Service - CrossChainPay

## Overview

The User Service is a Spring Boot microservice that provides user authentication and management for the CrossChainPay platform. It implements JWT-based authentication with Spring Security and provides RESTful APIs for user registration, login, and profile management.

## Features

- ✅ User Registration
- ✅ User Login with JWT Authentication
- ✅ Secure Password Encryption using BCrypt
- ✅ JWT Token Generation and Validation
- ✅ User Profile Management
- ✅ Spring Security Integration
- ✅ MySQL Database Support
- ✅ Global Exception Handling

## Technologies Used

- **Spring Boot** 3.2.5
- **Spring Security** 6.2.4
- **Spring Data JPA** with Hibernate ORM
- **JWT (JJWT)** 0.11.5
- **MySQL** 8.2.0
- **Kotlin** 1.9.24
- **Maven** (Build Tool)

## Project Structure

```
user-service/
├── src/main/kotlin/org/example/userservice/
│   ├── controller/
│   │   └── UserController.kt          # REST API endpoints
│   ├── service/
│   │   └── UserService.kt             # Business logic
│   ├── repository/
│   │   └── UserRepository.kt           # Data access
│   ├── model/
│   │   └── User.kt                     # Entity model
│   ├── dto/
│   │   ├── AuthRequest.kt             # Request/Response DTOs
│   │   └── UserResponse.kt            # User response DTO
│   ├── security/
│   │   ├── JwtTokenProvider.kt        # JWT token generation/validation
│   │   └── JwtAuthenticationFilter.kt # JWT authentication filter
│   ├── config/
│   │   └── SecurityConfig.kt          # Spring Security configuration
│   ├── exception/
│   │   ├── CustomExceptions.kt        # Custom exception classes
│   │   └── GlobalExceptionHandler.kt  # Global exception handler
│   ├── util/
│   │   └── RequestUtil.kt             # Utility functions
│   └── UserServiceApplication.kt      # Application entry point
├── src/main/resources/
│   ├── application.properties          # Application configuration
│   └── db/
│       └── schema.sql                  # Database schema
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

## Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email)
);
```

## API Endpoints

### 1. Register User

**Endpoint:** `POST /users/register`

**Description:** Register a new user account

**Request Body:**
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePassword123"
}
```

**Response (201 Created):**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "expiresIn": 86400000
}
```

**Error Responses:**
- `409 Conflict`: User with email already exists
- `400 Bad Request`: Invalid input data

---

### 2. Login User

**Endpoint:** `POST /users/login`

**Description:** Authenticate user and get JWT token

**Request Body:**
```json
{
    "email": "john@example.com",
    "password": "securePassword123"
}
```

**Response (200 OK):**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "expiresIn": 86400000
}
```

**Error Responses:**
- `404 Not Found`: User not found
- `401 Unauthorized`: Invalid email or password
- `400 Bad Request`: Invalid input data

---

### 3. Get User Profile

**Endpoint:** `GET /users/profile`

**Description:** Get authenticated user's profile information (Requires JWT Token)

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "createdAt": "2026-06-04 23:18:00"
}
```

**Error Responses:**
- `401 Unauthorized`: Invalid or missing token
- `404 Not Found`: User not found

---

### 4. Get User by ID

**Endpoint:** `GET /users/{userId}`

**Description:** Get user profile by ID (Requires JWT Token)

**Path Parameters:**
- `userId` (Long): User ID

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "createdAt": "2026-06-04 23:18:00"
}
```

**Error Responses:**
- `401 Unauthorized`: Invalid or missing token
- `404 Not Found`: User not found

---

## Configuration

### application.properties

```properties
# Server Configuration
spring.application.name=user-service
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/crosschainpay_users
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
jwt.secret=your_secret_key_change_this_in_production_environment_with_a_strong_secret_key_32_characters_or_more
jwt.expiration=86400000
```

### Important Configuration Notes

1. **Database Setup:**
   - Create MySQL database: `crosschainpay_users`
   - Update `spring.datasource.url`, `username`, and `password` as per your setup

2. **JWT Secret:**
   - Change `jwt.secret` to a strong, unique key in production
   - Minimum recommended length: 32 characters

3. **JWT Expiration:**
   - Default: 86400000 ms (24 hours)
   - Adjust as per your security requirements

## Setup and Installation

### Prerequisites

- Java 21 or higher
- Maven 3.8.0 or higher
- MySQL 8.0 or higher

### Steps

1. **Clone the repository:**
```bash
git clone <repository-url>
cd CrossChainPay/user-service
```

2. **Create MySQL database:**
```sql
CREATE DATABASE crosschainpay_users CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **Update Database Configuration:**
Edit `src/main/resources/application.properties` with your MySQL credentials

4. **Build the Project:**
```bash
mvn clean install
```

5. **Run the Application:**
```bash
mvn spring-boot:run
```
or
```bash
java -jar target/user-service-1.0-SNAPSHOT.jar
```

The service will start on `http://localhost:8081`

## Security Features

### Password Security
- Passwords are encrypted using **BCrypt** algorithm
- No plain-text passwords stored in the database

### JWT Authentication
- Token-based authentication using **JWT (JSON Web Token)**
- Secure token generation and validation
- Token expiration handling
- Token signature verification using HMAC-SHA512

### Spring Security Configuration
- CSRF protection disabled (for REST APIs)
- Stateless session management
- Public endpoints: `/users/register`, `/users/login`
- Protected endpoints: `/users/profile`, `/users/{userId}`
- Custom JWT authentication filter

## Exception Handling

The application includes a global exception handler that provides meaningful error responses:

### Custom Exceptions

1. **UserAlreadyExistsException** (409 Conflict)
   - Thrown when user tries to register with existing email

2. **UserNotFoundException** (404 Not Found)
   - Thrown when user is not found by ID or email

3. **InvalidCredentialsException** (401 Unauthorized)
   - Thrown when login credentials are invalid

## Example API Usage

### Register a New User
```bash
curl -X POST http://localhost:8081/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8081/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Get Profile (with JWT Token)
```bash
curl -X GET http://localhost:8081/users/profile \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

## Authentication Flow

```
1. User sends registration/login credentials
   ↓
2. Server validates credentials
   ↓
3. Server generates JWT token
   ↓
4. Server returns token and user info
   ↓
5. Client stores token
   ↓
6. For protected endpoints:
   - Client sends token in Authorization header
   - JWT filter extracts and validates token
   - Request is processed if token is valid
```

## Troubleshooting

### Database Connection Issues
- Ensure MySQL is running
- Verify database credentials in `application.properties`
- Check if database `crosschainpay_users` exists

### JWT Token Errors
- Invalid token format: Ensure token is sent as `Bearer <token>`
- Expired token: Request a new token by logging in again
- Invalid signature: Ensure JWT secret matches on all instances

### Build Failures
```bash
# Clear Maven cache and rebuild
mvn clean install -X
```

## Performance Considerations

- Database queries are indexed on email for faster lookups
- JWT tokens are stateless and don't require database queries for validation
- BCrypt hashing is intentionally slow to prevent brute-force attacks

## Future Enhancements

- [ ] Email verification for new registrations
- [ ] Password reset functionality
- [ ] OAuth 2.0 integration
- [ ] Two-factor authentication (2FA)
- [ ] User role-based access control (RBAC)
- [ ] API rate limiting and throttling
- [ ] Audit logging for security events
- [ ] Refresh token implementation

## License

This project is part of the CrossChainPay microservices platform.

## Support

For issues or questions, please contact the development team or create an issue in the project repository.

---

**Last Updated:** June 4, 2026
**Version:** 1.0.0

