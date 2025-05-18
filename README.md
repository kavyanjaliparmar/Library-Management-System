# Library Management System

A comprehensive library management system built with Spring Boot and MySQL.

## Features

### Basic Features
- User Management (Admin/Student roles)
- Book Management (CRUD operations)
- Issue/Return System
- Availability Tracking
- Fine Calculation

### Advanced Features
- Reservation System
- PDF/Online Access
- Book Categories/Genres
- Search Suggestions
- Reports Generation

## Technical Stack
- Java 17
- Spring Boot 3.1.0
- Spring Security with JWT
- Spring Data JPA
- MySQL Database
- Maven

## Setup Instructions

1. Ensure you have Java 17 and MySQL installed
2. Configure MySQL credentials in `application.properties`
3. Run the application using: `mvn spring-boot:run`
4. Access the application at: `http://localhost:8080`

## Database Schema
The system uses the following main entities:
- Users (Admin/Student)
- Books
- BookCopies
- Transactions
- Reservations
- Categories
