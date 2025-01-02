# Learning Management System (LMS) - Backend

## Overview

The **Learning Management System (LMS)** backend is built using **Java Spring Boot** and designed to manage user roles, authentication, and role-based access to various system features. This backend facilitates the smooth operation of a Learning Management System, including user management, course enrollment, and role-specific operations.

---

## Features

### User Roles

- **Student**: Can view courses, enroll, and access learning materials.
- **Instructor**: Can create and manage courses.
- **Admin**: Manages user roles and overall system configurations.
- **User**: Basic system user with limited access.

### Authentication & Authorization

- Login functionality.
- Token-based authentication using JWT.
- Role-based URL access control.

### Core Operations

- Create, update, delete, and view courses.
- User registration and management.
- Role-specific access control for functions.

---

## Technologies Used

| **Component**         | **Technology**                   |
| --------------------- | -------------------------------- |
| **Backend Framework** | Java Spring Boot (version 3.4.1) |
| **Database**          | MongoDB                       |
| **Authentication**    | JWT                              |
| **Testing Tools**     | Postman                          |

---

## Project Structure

```plaintext
src/
|-- main/
|   |-- java/com/lms/
|   |   |-- controllers/    # REST controllers
|   |   |-- services/       # Business logic
|   |   |-- repositories/   # Database repositories
|   |   |-- models/         # Data models
|   |   |-- config/         # Security and app configuration
|   |-- resources/
|       |-- application.yml  # Application configuration
|-- test/
    |-- java/com/lms/        # Test cases
