# Enviro365 Waste Sorting API

## Project Overview  
Enviro365 Waste Sorting API is a RESTful API built with Spring Boot that enables communication between the mobile app's frontend and backend. It provides endpoints for managing waste categories, disposal guidelines, and recycling tips, promoting sustainable waste management practices.

## Key Features
- **Waste Category Lookup**: Retrieve waste categories and associated details.
- **Disposal Guidelines**: Fetch information on proper waste disposal methods.
- **Recycling Tips**: Provide users with recommendations for recycling different types of waste.
- **User Management**: Registration, role management, and authentication.
- **CRUD Operations**: Admins can create, update, delete, and retrieve data.
- **Authentication & Authorization**: Role-based access control ensures only authorized administrators can modify data, maintaining accuracy and integrity.

## Security Implementation  
To prevent unauthorized modifications, the API includes **JWT-based authentication** and **role-based authorization**:
- **Users** can only access public endpoints.
- **Admins** have access to management endpoints for users, waste categories, disposal guidelines, and recycling tips.
- Secure authentication is implemented using **Spring Security and JWT tokens**.

## API Endpoints
### Authentication
- **`POST /auth/login`** – Authenticate user and return a JWT token.

### Waste Categories
- **`GET /api/v1/categories`** – Retrieve all waste categories.
- **`POST /api/v1/categories`** – Create a new waste category (Admin only).
- **`PUT /api/v1/categories/{id}`** – Update a specific waste category (Admin only).
- **`DELETE /api/v1/categories/{id}`** – Delete a specific waste category (Admin only).

### Disposal Guidelines
- **`GET /api/v1/disposal-guidelines`** – Retrieve all disposal guidelines.
- **`POST /api/v1/disposal-guidelines`** – Create new disposal guidelines (Admin only).
- **`PATCH /api/v1/disposal-guidelines/{id}`** – Update disposal guidelines (Admin only).
- **`DELETE /api/v1/disposal-guidelines/{id}`** – Delete disposal guidelines (Admin only).
- **`GET /api/v1/disposal-guidelines/category/{categoryId}`** – Retrieve disposal guidelines for a specific waste category.

### Recycling Tips
- **`GET /api/v1/recycling-tips`** – Retrieve all recycling tips.
- **`POST /api/v1/recycling-tips`** – Create a new recycling tip (Admin only).
- **`PATCH /api/v1/recycling-tips/{id}`** – Update a recycling tip (Admin only).
- **`DELETE /api/v1/recycling-tips/{id}`** – Delete a recycling tip (Admin only).
- **`GET /api/v1/recycling-tips/category/{categoryId}`** – Retrieve recycling tips for a specific waste category.

### Authentication
- **`POST /auth/login`** – Authenticate a user and generate a JWT token.
  
## User Management Endpoints
The `UserController` handles user registration, retrieval, and role management:
- **`POST /api/v1/users/register`** – Register a new user (default role: `USER`).
- **`POST /api/v1/users/add-role/{username}`** – Assign a role to an existing user (Admin only).
- **`GET /api/v1/users`** – Retrieve all users (Admin only).
- **`GET /api/v1/users/{username}`** – Retrieve user details by username (Admin only).
- **`PUT /api/v1/users/{username}`** – Update user details (Admin only).
- **`DELETE /api/v1/users/{username}`** – Delete a user (Admin only).

## Technology Stack
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: H2 Database
- **Authentication**: JWT (JSON Web Token)
- **API Documentation**: OpenAPI (Swagger)
- **Testing**: JUnit, Postman

## Installation & Setup
### Prerequisites
- Java 17+
- Maven
- PostgreSQL/MySQL (Configured in `application.properties`)
- Spring Boot dependencies

### Installation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/RNBaloyi/enviro365-waste-sorting-api.git
   cd enviro365-waste-sorting-api
   ```
2. Configure database connection settings in `application.properties`.
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```

### Accessing the API
Once the application is running, you can access the API at `http://localhost:8081`.

### Swagger Documentation
- Swagger UI: Access interactive API documentation at `http://localhost:8081/swagger-ui.html`.

## Error Handling & Validation
- **Error Responses**: The API returns structured JSON responses with meaningful error messages for incorrect or failed operations.
- **Input Validation**: Ensures data integrity using Spring Boot validation annotations.
