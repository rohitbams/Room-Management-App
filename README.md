# Room Management App

> A full-stack event management application enabling organisers to book rooms and attendees to register for events through multiple client interfaces.

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.6.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Team](#team)

---

## Overview

A comprehensive room booking system built using **Agile/Scrum methodology** with **Test-Driven Development (TDD)**. The system provides a RESTful backend API consumed by both terminal (CLI) and graphical (GUI) clients, enabling seamless event management and attendee coordination.

### Core Capabilities

- **3 User Roles**: Organiser, Attendee, Admin with distinct permissions
- **11 REST API Endpoints** for complete CRUD operations
- **2 Client Interfaces**: Terminal CLI (897 LOC) and Java Swing GUI
- **Real-time Capacity Management**: Automatic tracking and conflict prevention
- **Comprehensive Testing**: 66 test cases across 8 test suites

---

## Features

### User Management
- Role-based registration (Organiser/Attendee/Admin)
- Secure authentication with username/password
- Session management across multiple clients
- Duplicate username prevention

### Event & Room Management
- **For Organisers:**
  - Create bookings with date, time, and duration (1-8 hours)
  - View and manage personal bookings
  - Access attendee lists with real-time counts
  - Cancel bookings with automatic capacity release
  
- **For Attendees:**
  - Browse available events with capacity indicators
  - Register/unregister from events
  - View personal registered bookings
  - See unavailable events for future reference

- **For Admins:**
  - System-wide monitoring dashboard
  - View all users, rooms, and bookings
  - Add/remove rooms from the system

### Smart Business Logic
- Automatic room capacity enforcement
- Double-booking prevention with time slot validation
- Duplicate registration detection
- Real-time availability updates
- Comprehensive error handling

---

## Architecture

### Layered Architecture Pattern
```
┌────────────────────────────────────┐
│         Client Layer               │
│  (Terminal CLI / Java Swing GUI)   │
└──────────────┬─────────────────────┘
               │ REST/JSON
┌──────────────▼─────────────────────┐
│         Controller Layer           │
│       (REST API Endpoints)         │
└──────────────┬─────────────────────┘
               │
┌──────────────▼─────────────────────┐
│          Service Layer             │
│         (Business Logic)           │
└──────────────┬─────────────────────┘
               │
┌──────────────▼─────────────────────┐
│         Repository Layer           │
│       (Data Access - JPA)          │
└──────────────┬─────────────────────┘
               │
┌──────────────▼─────────────────────┐
│         Database Layer             │
│    (H2 In-Memory Database)         │
└────────────────────────────────────┘
```

### Technology Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java 11, Spring Boot 2.6.7, Spring Data JPA |
| **Database** | H2 (in-memory) |
| **API** | RESTful with JSON |
| **Terminal Client** | Java Console Application |
| **GUI Client** | Java Swing |
| **Build Tool** | Maven |
| **Testing** | JUnit, Mockito |
| **Version Control** | Git, GitLab |

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher
- **Maven**: Version 3.6.0 or higher
- **Git**: For cloning the repository

Verify installations:
```bash
java -version
mvn -version
git --version
```

### Installation

1. **Clone the repository**
```bash
   git clone git@github.com:rohitbams/Room-Management-App.git
   cd p3-code
```

2. **Build the project**
```bash
   mvn clean install
```

3. **Run tests** (optional but recommended)
```bash
   mvn clean test
```

---

## Usage

### Starting the Server
```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`

### Running the Terminal Client
```bash
mvn compile exec:java -Dexec.mainClass=com.stacs.cs5031.p3.client.terminal.TerminalClient
```

**Terminal Features:**
- Interactive menu system (14 commands)
- User-friendly input validation
- Date format: DD-MM-YYYY HH:MM
- Duration input in hours (1-8)

### Running the GUI Client
```bash
mvn compile exec:java -Dexec.mainClass=com.stacs.cs5031.p3.client.gui.login.LoginGUI
```

**GUI Features:**
- Modern Java Swing interface
- Visual feedback and error messages
- Registration and booking forms
- Attendee list views

### Default Test Accounts

The system initializes with test accounts for development:

| Username | Password | Role |
|----------|----------|------|
| `organiser` | `test` | Organiser |
| `attendee` | `test` | Attendee |
| `admin` | `test` | Admin |

---

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users/register` | Register new user |
| POST | `/users/login` | User login |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| GET | `/users/by-username/{username}` | Get user by username |
| DELETE | `/users/{id}` | Delete user |

### Attendee Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/attendees` | Get all attendees |
| GET | `/attendees/{id}` | Get attendee by ID |
| GET | `/attendees/{id}/available-bookings` | Get available bookings |
| GET | `/attendees/{id}/registered-bookings` | Get registered bookings |
| POST | `/attendees/{attendeeId}/register/{bookingId}` | Register for booking |

### Organiser Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/organisers/{id}/create-booking` | Create new booking |
| GET | `/organisers/{id}/bookings` | Get organiser's bookings |
| DELETE | `/organisers/{id}/bookings/{bookingId}` | Cancel booking |

**Example Request:**
```json
POST /api/users/register
{
  "name": "John Doe",
  "username": "johndoe",
  "password": "password123",
  "role": "ORGANISER"
}
```

---

## Testing

<!-- ### Test Coverage

- **Total Test Cases**: 66 across 8 test classes
- **Entity Tests**: 12 (User, Attendee models)
- **Service Tests**: 21 (Business logic validation)
- **Controller Tests**: 24 (API endpoint verification)
- **Repository Tests**: 5 (Custom JPQL queries)
- **Integration Tests**: 4 (End-to-end workflows) -->

### Running Tests
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run tests with coverage report
mvn clean test jacoco:report
```

### Test Strategy

- **Test-Driven Development (TDD)**: Tests written before implementation
- **Unit Testing**: Isolated component testing
- **Integration Testing**: Client-server communication
- **Edge Case Testing**: Malformed input, capacity limits, conflicts

---

## Project Structure
```
p3-code/
├── src/
│   ├── main/
│   │   ├── java/com/stacs/cs5031/p3/
│   │   │   ├── client/
│   │   │   │   ├── gui/           # Java Swing GUI components
│   │   │   │   └── terminal/      # Terminal client
│   │   │   ├── server/
│   │   │   │   ├── config/        # Configuration & DataInitializer
│   │   │   │   ├── controller/    # REST API controllers
│   │   │   │   ├── dto/           # Data Transfer Objects
│   │   │   │   ├── exception/     # Custom exceptions
│   │   │   │   ├── mapper/        # Entity-DTO mappers
│   │   │   │   ├── model/         # Entity classes
│   │   │   │   ├── repository/    # JPA repositories
│   │   │   │   └── service/       # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── test/
│       └── java/com/stacs/cs5031/p3/
│           └── server/             # Test classes (66 tests)
├── pom.xml
└── README.md
```

---

## Troubleshooting

### Common Issues

**Port 8080 already in use:**
```bash
# Find and kill the process
lsof -i :8080
kill -9 <PID>
```

**Maven build fails:**
```bash
# Clean and rebuild
mvn clean
mvn install -U
```

**H2 Console Access** (for debugging):
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)
```

---

## Team

This project was developed by a team of 4 using **Agile/Scrum methodology**:
- Weekly sprints with rotating Scrum Master
- Daily stand-ups via Microsoft Teams
- Sprint planning, reviews, and retrospectives
- Collaborative development via GitLab

### Development Practices
- Test-Driven Development (TDD)
- Continuous Integration via GitLab CI/CD
- Code reviews and pair programming
- JavaDoc documentation
- Git version control with feature branches

---

## License

This project was developed as part of CS5031 - Software Engineering Practice at the University of St Andrews.

---

## Acknowledgments

- University of St Andrews School of Computer Science
- CS5031 Module Team
- All team members for their collaborative effort

---


<p align="center">Made with ☕</p>