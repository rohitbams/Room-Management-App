# Room Management App

> A full-stack event management application enabling organisers to book rooms and attendees to register for events through multiple client interfaces.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Team](#team)

---

## Overview

This application is a comprehensive **event management platform** designed to streamline the process of organising and attending events. It serves as a central hub where event organisers can book rooms for their events, and attendees can discover and register for events that interest them.

**Real-World Use Case:**
Imagine a university, conference center, or community hub where multiple events happen daily. Event organisers (speakers, workshop leaders, club presidents) need to reserve rooms for their events. Meanwhile, students, employees, or community members want to browse available events and sign up to attend. This application bridges that gap by providing:

- **For Event Organisers:** A simple way to book rooms, specify event details (date, time, duration), and track who's attending their events
- **For Attendees:** An easy-to-use platform to browse available events, register for ones they're interested in, and manage their event schedule
- **For Administrators:** System-wide oversight to manage rooms, monitor bookings, and ensure smooth operations

The platform is accessible through both a modern GUI interface (perfect for everyday use) and a terminal CLI (ideal for power users or remote access), making it flexible for different user preferences and scenarios.

### Technical Overview

A comprehensive room booking system built by a 4-person software development team using **Agile/Scrum methodology** with **Test-Driven Development (TDD)**. A Spring Boot application written following OOP principles, this system provides a RESTful backend API consumed by both terminal (CLI) and graphical (GUI) clients, enabling seamless event management and attendee coordination.

### Core Capabilities

- **3 User Roles**: Organiser, Attendee, Admin with distinct permissions
- **42 REST API Endpoints** for complete CRUD operations
- **2 Client Interfaces**: Terminal CLI and Java Swing GUI
- **Real-time Capacity Management**: Automatic capacity tracking and conflict prevention
- **Comprehensive Testing**: 227 test cases across 27 test classes

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

## Screenshots

### Authentication & Registration

<div align="center">

#### GUI Interface
<table>
  <tr>
    <td align="center">
      <img src="screenshots/GUI_Login.png" alt="GUI Login" width="400"/>
      <br/>
      <b>Login Screen</b>
    </td>
    <td align="center">
      <img src="screenshots/GUI__Register.png" alt="GUI Registration" width="400"/>
      <br/>
      <b>User Registration</b>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <img src="screenshots/GUI_Registration_succes.png" alt="Registration Success" width="400"/>
      <br/>
      <b>Registration Success Confirmation</b>
    </td>
  </tr>
</table>

#### CLI Interface
<table>
  <tr>
    <td align="center">
      <img src="screenshots/CLI_Login.png" alt="CLI Login" width="400"/>
      <br/>
      <b>Terminal Login</b>
    </td>
    <td align="center">
      <img src="screenshots/CLI_Login_error.png" alt="CLI Login Error" width="400"/>
      <br/>
      <b>Error Handling</b>
    </td>
  </tr>
</table>

</div>

---

### Admin Features

<div align="center">

#### GUI Admin Dashboard
<img src="screenshots/GUI_Admin_View.png" alt="Admin Dashboard" width="600"/>
<br/>
<b>Admin Dashboard - System Overview</b>

#### CLI Admin Operations
<table>
  <tr>
    <td align="center">
      <img src="screenshots/CLI_Admin_Menu.png" alt="Admin Menu" width="400"/>
      <br/>
      <b>Admin Menu</b>
    </td>
    <td align="center">
      <img src="screenshots/CLI_Admin_ViewAllRooms.png" alt="View All Rooms" width="400"/>
      <br/>
      <b>View All Rooms</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="screenshots/CLI_Admin_ViewAllAttendees.png" alt="View All Attendees" width="400"/>
      <br/>
      <b>View All Attendees</b>
    </td>
    <td align="center">
      <img src="screenshots/CLI_Admin_ViewAllOrganisers.png" alt="View All Organisers" width="400"/>
      <br/>
      <b>View All Organisers</b>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <img src="screenshots/CLI_Admin_RemoveRoom.png" alt="Remove Room" width="400"/>
      <br/>
      <b>Remove Room Functionality</b>
    </td>
  </tr>
</table>

</div>

---

### Organiser Features

<div align="center">

#### GUI Organiser Interface
<table>
  <tr>
    <td align="center">
      <img src="screenshots/GUI_Organiser_Home.png" alt="Organiser Home" width="400"/>
      <br/>
      <b>Organiser Dashboard</b>
    </td>
    <td align="center">
      <img src="screenshots/GUI_Organiser_Create_booking.png" alt="Create Booking" width="400"/>
      <br/>
      <b>Create New Booking</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="screenshots/GUI_Organiser_Booking_success.png" alt="Booking Success" width="400"/>
      <br/>
      <b>Booking Created Successfully</b>
    </td>
    <td align="center">
      <img src="screenshots/GUI_Organiser_My_Bookings.png" alt="My Bookings" width="400"/>
      <br/>
      <b>View My Bookings</b>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <img src="screenshots/GUI_Organiser_Select_Booking.png" alt="Select Booking" width="400"/>
      <br/>
      <b>Select & Manage Booking</b>
    </td>
  </tr>
</table>

#### CLI Organiser Operations
<table>
  <tr>
    <td align="center">
      <img src="screenshots/CLI_Organiser_Login.png" alt="Organiser Login" width="400"/>
      <br/>
      <b>Organiser Login</b>
    </td>
    <td align="center">
      <img src="screenshots/CLI_Organiser_CreateBooking.png" alt="Create Booking CLI" width="400"/>
      <br/>
      <b>Create Booking</b>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <img src="screenshots/CLI_Organiser_My_Bookings.png" alt="View Bookings CLI" width="400"/>
      <br/>
      <b>View My Bookings</b>
    </td>
  </tr>
</table>

</div>

---

### Attendee Features

<div align="center">

#### GUI Attendee Interface
<table>
  <tr>
    <td align="center">
      <img src="screenshots/GUI_Attendee_Register.png" alt="Attendee Registration" width="400"/>
      <br/>
      <b>Attendee Registration</b>
    </td>
    <td align="center">
      <img src="screenshots/GUI_Attendee_Home.png" alt="Attendee Home" width="400"/>
      <br/>
      <b>Attendee Dashboard</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="screenshots/GUI_Attendee_Register_Event.png" alt="Register for Event" width="400"/>
      <br/>
      <b>Browse & Register for Events</b>
    </td>
    <td align="center">
      <img src="screenshots/GUI_Attendee__Registration_Succes.png" alt="Registration Success" width="400"/>
      <br/>
      <b>Event Registration Success</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="screenshots/GUI_Attendee_My_Events.png" alt="My Events" width="400"/>
      <br/>
      <b>View My Registered Events</b>
    </td>
    <td align="center">
      <img src="screenshots/GUI_Attendee_Deregister.png" alt="Deregister" width="400"/>
      <br/>
      <b>Deregister from Event</b>
    </td>
  </tr>
</table>

#### CLI Attendee Operations
<img src="screenshots/CLI_Attendee_RegisterBooking_ViewMyBookings.png" alt="Attendee CLI Operations" width="600"/>
<br/>
<b>Register for Event & View My Bookings</b>

</div>

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
| **Backend** | Java 21, Spring Boot 3.4.4, Spring Data JPA, Spring Security |
| **Database** | H2 (in-memory) |
| **API** | RESTful with JSON |
| **Terminal Client** | Java Console Application |
| **GUI Client** | Java Swing, JavaFX 20 |
| **Build Tool** | Maven |
| **Testing** | JUnit 5, Mockito, Spring Boot Test |
| **Version Control** | Git, GitLab |

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 21 or higher
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

The system initialises with test accounts for development:

| Username | Password | Role |
|----------|----------|------|
| `organiser` | `test` | Organiser |
| `attendee` | `test` | Attendee |
| `admin` | `test` | Admin |

---

## API Documentation

### Base URL
```
http://localhost:8080
```

### User Endpoints (7 endpoints)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users/register` | Register new user |
| POST | `/users/login` | User login |
| POST | `/users/logout` | User logout |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| GET | `/users/by-username/{username}` | Get user by username |
| DELETE | `/users/{id}` | Delete user |

### Attendee Endpoints (7 endpoints)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/attendees` | Get all attendees |
| GET | `/attendees/{id}` | Get attendee by ID |
| GET | `/attendees/{id}/available-bookings` | Get available bookings |
| GET | `/attendees/{id}/unavailable-bookings` | Get unavailable bookings |
| GET | `/attendees/{id}/registered-bookings` | Get registered bookings |
| POST | `/attendees/{attendeeId}/register/{bookingId}` | Register for booking |
| DELETE | `/attendees/{attendeeId}/cancel/{bookingId}` | Cancel registration |

### Organiser Endpoints (8 endpoints)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/organiser/create-organiser` | Create new organiser |
| GET | `/organisers` | Get all organisers |
| GET | `/organiser/available-rooms` | Get available rooms |
| GET | `/organiser/my-bookings/{organiserId}` | Get organiser's bookings |
| GET | `/organiser/{organiserId}/my-bookings/{bookingId}` | Get specific booking details |
| GET | `/organiser/{organiserId}/my-bookings/{bookingId}/attendees` | Get booking attendees |
| POST | `/organiser/create-booking/{organiserId}` | Create new booking |
| DELETE | `/organiser/cancel-booking/{bookingId}/{organiserId}` | Cancel booking |

### Booking Endpoints (5 endpoints)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/bookings` | Get all bookings |
| GET | `/api/bookings/{bookingId}` | Get booking by ID |
| POST | `/api/bookings/organiser/{organiserId}` | Create booking for organiser |
| DELETE | `/api/bookings/{bookingId}` | Delete booking |
| POST | `/api/bookings/{bookingId}/attendees/{attendeeId}` | Add attendee to booking |

### Room Endpoints (5 endpoints)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/rooms/all` | Get all rooms |
| GET | `/rooms/{id}` | Get room by ID |
| GET | `/rooms/available` | Get available rooms |
| POST | `/rooms/{id}/book` | Book a room |
| POST | `/rooms/{id}/makeAvailable` | Make room available |

### Admin Endpoints (5 endpoints)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/rooms` | Get all rooms (admin view) |
| GET | `/admin/attendees` | Get all attendees (admin view) |
| GET | `/admin/organisers` | Get all organisers (admin view) |
| POST | `/admin/rooms` | Add new room |
| DELETE | `/admin/rooms/{roomId}` | Delete room |

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

### Test Coverage

- **Total Test Cases**: 227 test methods across 27 test classes
- **Controller Tests**: 72 tests (6 controllers)
  - UserControllerTest, AttendeeControllerTest, BookingControllerTest
  - OrganiserControllerTest, RoomControllerTest, AdminControllerTest
- **Service Tests**: 63 tests (6 services)
  - UserServiceTest, AttendeeServiceTest, BookingServiceTest
  - OrganiserServiceTest, RoomServiceTest, AdminServiceTest
- **Repository Tests**: 29 tests (5 repositories)
  - Custom JPQL queries and data persistence validation
- **Model/Entity Tests**: 38 tests (6 entities)
  - User, Attendee, Organiser, Booking, Room, Admin
- **DTO Tests**: 20 tests (4 DTOs)
  - Data Transfer Object validation and mapping
- **Integration Tests**: 5 tests
  - End-to-end workflows and client-server communication

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
│   │   │   │   ├── config/        # Configuration & DataInitialiser
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
│           └── server/             # Test classes (227 tests across 27 classes)
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
