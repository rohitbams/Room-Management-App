package com.stacs.cs5031.p3.client.terminal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class TerminalClient {


    private static final String BASE_URL = "http://localhost:8080/api";
    private static RestTemplate restTemplate = new RestTemplate();
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, String> currentUser = null;
    private static boolean running = true;

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            if (currentUser == null) {
                showWelcomeMenu();
            } else {
                String role = currentUser.get("role");
                if ("ORGANISER".equals(role)) {
                    showOrganiserMenu();
                } else if ("ATTENDEE".equals(role)) {
                    showAttendeeMenu();
                } else if ("ADMIN".equals(role)) {
                    showAdminMenu();
                }
            }
        }

        scanner.close();
    }

    /**
     * This method display user login menu.
     */
    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = handleLogin(username, password);

        if (success) {
            System.out.println("Login successful. Welcome, " + currentUser.get("name") + "!");
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
    }

    /**
     * This method handles user login.
     * @param username user's username
     * @param password user's password
     * @return true if login is successful and user data is received, false if login failed or an error occurred
     */
    static boolean handleLogin(String username, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", username);
            requestBody.put("password", password);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            Map<String, Object> response = restTemplate.postForObject(
                    BASE_URL + "/users/login",
                    request,
                    Map.class
            );

            if (response != null) {
                currentUser = new HashMap<>();
                currentUser.put("id", String.valueOf(response.get("id")));
                currentUser.put("username", (String) response.get("username"));
                currentUser.put("name", (String) response.get("name"));
                currentUser.put("role", (String) response.get("role"));

                return true;            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method displays user registration menu.
     */
    private static void register() {
        System.out.println("\n=== Registration ===");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Select role:");
        System.out.println("1. Organiser");
        System.out.println("2. Attendee");
        System.out.print("Enter choice (1 or 2): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String role = roleChoice == 1 ? "ORGANISER" : "ATTENDEE";

        boolean success = handleRegistration(name, username, password, role);

        if (success) {
            System.out.println("Registration successful! Please login.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    /**
     * This method handles user registration.
     * @param name user's name
     * @param username user's username
     * @param password user's password
     * @param role user's role (organiser or attendee)
     * @return true if registration is successful, false if registration failed or an error occurred
     */
    static boolean handleRegistration(String name, String username, String password, String role) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", name);
            requestBody.put("username", username);
            requestBody.put("password", password);
            requestBody.put("role", role);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            Map<String, Object> response = restTemplate.postForObject(
                    BASE_URL + "/users/register",
                    request,
                    Map.class
            );

            return response != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method shows the welcome menu which is the first menu that is displayed
     * when the terminal is started.
     */
    private static void showWelcomeMenu() {
        System.out.println("=== Room Booking System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter the number next to your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                login();
                break;            case 2:
                register();
                break;            case 3:
                System.exit(0);
                break;            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void showOrganiserMenu() {

    }

    private static void showAttendeeMenu() {

    }

    private static void showAdminMenu() {

    }

    // for testing purposes only
    static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    static Map<String, String> getCurrentUser() {
        return currentUser;
    }

    static void setCurrentUser(Map<String, String> user) {
        currentUser = user;
    }

    static void setRestTemplate(RestTemplate template) {
        restTemplate = template;
    }

    static void setRunning(boolean isRunning) {
        running = isRunning;
    }


}

//package com.stacs.cs5031.p3.client.terminal;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import com.stacs.cs5031.p3.server.dto.BookingDto;
//import com.stacs.cs5031.p3.server.dto.RoomDto;
//import com.stacs.cs5031.p3.server.dto.UserDto;
//import com.stacs.cs5031.p3.server.dto.LoginRequest;
////import com.stacs.cs5031.p3.server.dto.LoginResponse;
//import com.stacs.cs5031.p3.server.dto.RegistrationRequest;
//
///**
// * Terminal client for the Room Booking System.
// * Provides a text-based interface for users to interact with the system.
// */
//@SpringBootApplication
//public class TerminalClient implements CommandLineRunner {
//
//    private static final String API_BASE_URL = "http://localhost:8080/api";
//    private static final Scanner scanner = new Scanner(System.in);
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//    private static String authToken;
//    private static UserDto currentUser;
//    private static boolean isRunning = true;
//
//    private final RestTemplate restTemplate;
//
//    public TerminalClient(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    public static void main(String[] args) {
//        // Run as a standalone application, not a web application
//        new SpringApplicationBuilder(TerminalClient.class)
//            .web(WebApplicationType.NONE)
//            .run(args);
//    }
//
//    @Override
//    public void run(String... args) {
//        System.out.println("===== Room Booking System Terminal Client =====");
//
//        while (isRunning) {
//            if (currentUser == null) {
//                showLoginMenu();
//            } else if ("ORGANISER".equals(currentUser.getUserType())) {
//                showOrganiserMenu();
//            } else if ("ATTENDEE".equals(currentUser.getUserType())) {
//                showAttendeeMenu();
//            } else {
//                System.out.println("Unknown user type. Logging out...");
//                logout();
//            }
//        }
//
//        System.out.println("Thank you for using the Room Booking System!");
//        System.exit(0);
//    }
//
//    private void showLoginMenu() {
//        System.out.println("\n===== Login Menu =====");
//        System.out.println("1. Login");
//        System.out.println("2. Register");
//        System.out.println("0. Exit");
//        System.out.print("Enter your choice: ");
//
//        int choice = getIntInput();
//
//        switch (choice) {
//            case 1:
//                login();
//                break;
//            case 2:
//                register();
//                break;
//            case 0:
//                isRunning = false;
//                break;
//            default:
//                System.out.println("Invalid choice. Please try again.");
//        }
//    }
//
//    private void showOrganiserMenu() {
//        System.out.println("\n===== Organiser Menu =====");
//        System.out.println("Welcome, " + currentUser.getFullName() + " (Organiser)");
//        System.out.println("1. Create New Booking");
//        System.out.println("2. View My Bookings");
//        System.out.println("3. View All Rooms");
//        System.out.println("4. View All Bookings");
//        System.out.println("5. Manage Booking");
//        System.out.println("9. Logout");
//        System.out.println("0. Exit");
//        System.out.print("Enter your choice: ");
//
//        int choice = getIntInput();
//
//        switch (choice) {
//            case 1:
//                createBooking();
//                break;
//            case 2:
//                viewMyBookings();
//                break;
//            case 3:
//                viewAllRooms();
//                break;
//            case 4:
//                viewAllBookings();
//                break;
//            case 5:
//                manageBooking();
//                break;
//            case 9:
//                logout();
//                break;
//            case 0:
//                isRunning = false;
//                break;
//            default:
//                System.out.println("Invalid choice. Please try again.");
//        }
//    }
//
//    private void showAttendeeMenu() {
//        System.out.println("\n===== Attendee Menu =====");
//        System.out.println("Welcome, " + currentUser.getName() + " (Attendee)");
//        System.out.println("1. View Available Bookings");
//        System.out.println("2. View My Registrations");
//        System.out.println("3. Register for Booking");
//        System.out.println("4. Unregister from Booking");
//        System.out.println("9. Logout");
//        System.out.println("0. Exit");
//        System.out.print("Enter your choice: ");
//
//        int choice = getIntInput();
//
//        switch (choice) {
//            case 1:
//                viewAllBookings();
//                break;
//            case 2:
//                viewMyRegistrations();
//                break;
//            case 3:
//                registerForBooking();
//                break;
//            case 4:
//                unregisterFromBooking();
//                break;
//            case 9:
//                logout();
//                break;
//            case 0:
//                isRunning = false;
//                break;
//            default:
//                System.out.println("Invalid choice. Please try again.");
//        }
//    }
//
//    private void login() {
//        System.out.println("\n===== Login =====");
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine();
//
//        System.out.print("Enter password: ");
//        String password = scanner.nextLine();
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername(username);
//        loginRequest.setPassword(password);
//
//        try {
//            String url = API_BASE_URL + "/auth/login";
//            LoginResponse response = restTemplate.postForObject(url, loginRequest, LoginResponse.class);
//
//            authToken = response.getToken();
//            currentUser = response.getUser();
//            System.out.println("Login successful! Welcome, " + currentUser.getFullName());
//        } catch (HttpClientErrorException e) {
//            System.out.println("Login failed: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error during login: " + e.getMessage());
//        }
//    }
//
//    private void register() {
//        System.out.println("\n===== Register =====");
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine();
//
//        System.out.print("Enter password: ");
//        String password = scanner.nextLine();
//
//        System.out.print("Enter full name: ");
//        String fullName = scanner.nextLine();
//
//        System.out.print("Enter email: ");
//        String email = scanner.nextLine();
//
//        System.out.println("Select user type:");
//        System.out.println("1. Attendee");
//        System.out.println("2. Organiser");
//        System.out.print("Enter your choice: ");
//        int typeChoice = getIntInput();
//
//        String userType;
//        if (typeChoice == 1) {
//            userType = "ATTENDEE";
//        } else if (typeChoice == 2) {
//            userType = "ORGANISER";
//        } else {
//            System.out.println("Invalid choice. Defaulting to Attendee.");
//            userType = "ATTENDEE";
//        }
//
//        RegistrationRequest request = new RegistrationRequest();
//        request.setUsername(username);
//        request.setPassword(password);
//        request.setName(fullName);
////        request.setEmail(email);
//        request.setUserType(userType);
//
//        try {
//            String url = API_BASE_URL + "/auth/register";
//            UserDto response = restTemplate.postForObject(url, request, UserDto.class);
//            System.out.println("Registration successful! Please login.");
//        } catch (HttpClientErrorException e) {
//            System.out.println("Registration failed: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error during registration: " + e.getMessage());
//        }
//    }
//
//    private void logout() {
//        authToken = null;
//        currentUser = null;
//        System.out.println("Logged out successfully.");
//    }
//
//    private void createBooking() {
//        System.out.println("\n===== Create New Booking =====");
//
//        // Get available rooms
//        List<RoomDto> rooms = getAllRooms();
//        if (rooms.isEmpty()) {
//            System.out.println("No rooms available.");
//            return;
//        }
//
//        // Display available rooms
//        System.out.println("Available Rooms:");
//        for (int i = 0; i < rooms.size(); i++) {
//            RoomDto room = rooms.get(i);
//            System.out.println((i + 1) + ". " + room.getName() + " (Capacity: " + room.getCapacity() + ")");
//        }
//
//        // Get room selection
//        System.out.print("Select room (1-" + rooms.size() + "): ");
//        int roomIndex = getIntInput() - 1;
//        if (roomIndex < 0 || roomIndex >= rooms.size()) {
//            System.out.println("Invalid room selection.");
//            return;
//        }
//
//        // Get booking details
//        System.out.print("Enter event name: ");
//        String eventName = scanner.nextLine();
//
//        System.out.print("Enter date (YYYY-MM-DD): ");
//        String dateStr = scanner.nextLine();
//
//        System.out.print("Enter time (HH:MM): ");
//        String timeStr = scanner.nextLine();
//
//        Date startTime;
//        try {
//            startTime = dateFormat.parse(dateStr + " " + timeStr);
//        } catch (ParseException e) {
//            System.out.println("Invalid date or time format.");
//            return;
//        }
//
//        System.out.print("Enter duration in minutes: ");
//        int duration = getIntInput();
//        if (duration <= 0) {
//            System.out.println("Duration must be positive.");
//            return;
//        }
//
//        // Create booking request
//        BookingDTO.BookingRequest request = new BookingDTO.BookingRequest();
//        request.setEventName(eventName);
//        request.setRoomId((long) rooms.get(roomIndex).getId());
//        request.setStartTime(startTime);
//        request.setDuration(duration);
//
//        try {
//            String url = API_BASE_URL + "/bookings?organiserId=" + currentUser.getId();
//            BookingDTO response = restTemplate.postForObject(url, request, BookingDTO.class);
//            System.out.println("Booking created successfully!");
//        } catch (HttpClientErrorException e) {
//            System.out.println("Failed to create booking: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error creating booking: " + e.getMessage());
//        }
//    }
//
//    private void viewMyBookings() {
//        if ("ORGANISER".equals(currentUser.getUserType())) {
//            System.out.println("\n===== My Created Bookings =====");
//            try {
//                String url = API_BASE_URL + "/bookings/organiser/" + currentUser.getId();
//                BookingDTO[] bookings = restTemplate.getForObject(url, BookingDTO[].class);
//
//                if (bookings == null || bookings.length == 0) {
//                    System.out.println("You haven't created any bookings yet.");
//                } else {
//                    displayBookings(bookings);
//                }
//            } catch (Exception e) {
//                System.out.println("Error retrieving bookings: " + e.getMessage());
//            }
//        }
//    }
//
//    private void viewMyRegistrations() {
//        if ("ATTENDEE".equals(currentUser.getUserType())) {
//            System.out.println("\n===== My Registered Bookings =====");
//            try {
//                String url = API_BASE_URL + "/bookings/attendee/" + currentUser.getId();
//                BookingDTO[] bookings = restTemplate.getForObject(url, BookingDTO[].class);
//
//                if (bookings == null || bookings.length == 0) {
//                    System.out.println("You haven't registered for any bookings yet.");
//                } else {
//                    displayBookings(bookings);
//                }
//            } catch (Exception e) {
//                System.out.println("Error retrieving registrations: " + e.getMessage());
//            }
//        }
//    }
//
//    private void viewAllRooms() {
//        System.out.println("\n===== All Rooms =====");
//        List<RoomDto> rooms = getAllRooms();
//
//        if (rooms.isEmpty()) {
//            System.out.println("No rooms available.");
//        } else {
//            for (RoomDto room : rooms) {
//                System.out.println("ID: " + room.getId() +
//                        " | Name: " + room.getName() +
//                        " | Capacity: " + room.getCapacity() +
//                        " | Available: " + (room.isAvailable() ? "Yes" : "No"));
//            }
//        }
//    }
//
//    private List<RoomDto> getAllRooms() {
//        try {
//            String url = API_BASE_URL + "/rooms/all";
//            RoomDto[] rooms = restTemplate.getForObject(url, RoomDto[].class);
//            return rooms != null ? List.of(rooms) : List.of();
//        } catch (Exception e) {
//            System.out.println("Error retrieving rooms: " + e.getMessage());
//            return List.of();
//        }
//    }
//
//    private void viewAllBookings() {
//        System.out.println("\n===== All Bookings =====");
//        try {
//            String url = API_BASE_URL + "/bookings";
//            BookingDTO[] bookings = restTemplate.getForObject(url, BookingDTO[].class);
//
//            if (bookings == null || bookings.length == 0) {
//                System.out.println("No bookings available.");
//            } else {
//                displayBookings(bookings);
//            }
//        } catch (Exception e) {
//            System.out.println("Error retrieving bookings: " + e.getMessage());
//        }
//    }
//
//    private void displayBookings(BookingDTO[] bookings) {
//        for (int i = 0; i < bookings.length; i++) {
//            BookingDTO booking = bookings[i];
//            System.out.println((i + 1) + ". " + booking.getEventName());
//            System.out.println("   Room: " + booking.getRoomName());
//            System.out.println("   Time: " + dateFormat.format(booking.getStartTime()));
//            System.out.println("   Duration: " + booking.getDuration() + " minutes");
//            System.out.println("   Organiser: " + booking.getOrganiserName());
//            System.out.println("   Attendees: " + booking.getCurrentAttendees() + "/" + booking.getMaxCapacity());
//            System.out.println();
//        }
//    }
//
//    private void manageBooking() {
//        if (!"ORGANISER".equals(currentUser.getUserType())) {
//            System.out.println("Only organisers can manage bookings.");
//            return;
//        }
//
//        System.out.println("\n===== Manage Booking =====");
//
//        // Get organiser's bookings
//        String url = API_BASE_URL + "/bookings/organiser/" + currentUser.getId();
//        BookingDTO[] bookings = restTemplate.getForObject(url, BookingDTO[].class);
//
//        if (bookings == null || bookings.length == 0) {
//            System.out.println("You have no bookings to manage.");
//            return;
//        }
//
//        // Display bookings
//        displayBookings(bookings);
//
//        // Select booking to manage
//        System.out.print("Select booking to manage (1-" + bookings.length + "): ");
//        int bookingIndex = getIntInput() - 1;
//        if (bookingIndex < 0 || bookingIndex >= bookings.length) {
//            System.out.println("Invalid booking selection.");
//            return;
//        }
//
//        BookingDTO selectedBooking = bookings[bookingIndex];
//
//        // Display management options
//        System.out.println("\nManage Booking: " + selectedBooking.getEventName());
//        System.out.println("1. View Attendees");
//        System.out.println("2. Edit Booking");
//        System.out.println("3. Delete Booking");
//        System.out.println("0. Back to Menu");
//        System.out.print("Enter your choice: ");
//
//        int choice = getIntInput();
//
//        switch (choice) {
//            case 1:
//                viewBookingAttendees(selectedBooking.getId());
//                break;
//            case 2:
//                editBooking(selectedBooking);
//                break;
//            case 3:
//                deleteBooking(selectedBooking.getId());
//                break;
//            case 0:
//                // Back to menu
//                break;
//            default:
//                System.out.println("Invalid choice.");
//        }
//    }
//
//    private void viewBookingAttendees(Long bookingId) {
//        System.out.println("\n===== Booking Attendees =====");
//        try {
//            String url = API_BASE_URL + "/bookings/" + bookingId;
//            BookingDTO booking = restTemplate.getForObject(url, BookingDTO.class);
//
//            if (booking.getAttendees() == null || booking.getAttendees().isEmpty()) {
//                System.out.println("No attendees registered for this booking.");
//            } else {
//                System.out.println("Attendees for: " + booking.getEventName());
//                int count = 1;
//                for (UserDTO attendee : booking.getAttendees()) {
//                    System.out.println(count + ". " + attendee.getFullName() + " (" + attendee.getEmail() + ")");
//                    count++;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Error retrieving booking details: " + e.getMessage());
//        }
//    }
//
//    private void editBooking(BookingDTO booking) {
//        System.out.println("\n===== Edit Booking =====");
//
//        // Get available rooms
//        List<RoomDto> rooms = getAllRooms();
//        if (rooms.isEmpty()) {
//            System.out.println("No rooms available.");
//            return;
//        }
//
//        // Get current booking details
//        System.out.println("Current Details:");
//        System.out.println("Event Name: " + booking.getEventName());
//        System.out.println("Room: " + booking.getRoomName());
//        System.out.println("Start Time: " + dateFormat.format(booking.getStartTime()));
//        System.out.println("Duration: " + booking.getDuration() + " minutes");
//
//        // Get new details (or keep current)
//        System.out.print("Enter new event name (or press Enter to keep current): ");
//        String eventName = scanner.nextLine();
//        if (eventName.isBlank()) {
//            eventName = booking.getEventName();
//        }
//
//        // Display available rooms
//        System.out.println("\nAvailable Rooms:");
//        for (int i = 0; i < rooms.size(); i++) {
//            RoomDto room = rooms.get(i);
//            System.out.println((i + 1) + ". " + room.getName() + " (Capacity: " + room.getCapacity() + ")");
//        }
//
//        System.out.print("Select new room (1-" + rooms.size() + ", or 0 to keep current): ");
//        int roomIndex = getIntInput() - 1;
//        Long roomId;
//        if (roomIndex == -1) {
//            roomId = booking.getRoomId();
//        } else if (roomIndex >= 0 && roomIndex < rooms.size()) {
//            roomId = (long) rooms.get(roomIndex).getId();
//        } else {
//            System.out.println("Invalid room selection. Keeping current room.");
//            roomId = booking.getRoomId();
//        }
//
//        System.out.print("Enter new date (YYYY-MM-DD, or press Enter to keep current): ");
//        String dateStr = scanner.nextLine();
//
//        System.out.print("Enter new time (HH:MM, or press Enter to keep current): ");
//        String timeStr = scanner.nextLine();
//
//        Date startTime;
//        if (dateStr.isBlank() || timeStr.isBlank()) {
//            startTime = booking.getStartTime();
//        } else {
//            try {
//                startTime = dateFormat.parse(dateStr + " " + timeStr);
//            } catch (ParseException e) {
//                System.out.println("Invalid date or time format. Keeping current time.");
//                startTime = booking.getStartTime();
//            }
//        }
//
//        System.out.print("Enter new duration in minutes (or 0 to keep current): ");
//        int duration = getIntInput();
//        if (duration == 0) {
//            duration = booking.getDuration();
//        }
//
//        // Update booking
//        BookingDTO.BookingRequest request = new BookingDTO.BookingRequest();
//        request.setEventName(eventName);
//        request.setRoomId(roomId);
//        request.setStartTime(startTime);
//        request.setDuration(duration);
//
//        try {
//            String url = API_BASE_URL + "/bookings/" + booking.getId();
//            restTemplate.put(url, request);
//            System.out.println("Booking updated successfully!");
//        } catch (HttpClientErrorException e) {
//            System.out.println("Failed to update booking: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error updating booking: " + e.getMessage());
//        }
//    }
//
//    private void deleteBooking(Long bookingId) {
//        System.out.println("\n===== Delete Booking =====");
//        System.out.print("Are you sure you want to delete this booking? (y/n): ");
//        String confirmation = scanner.nextLine().trim().toLowerCase();
//
//        if (confirmation.equals("y") || confirmation.equals("yes")) {
//            try {
//                String url = API_BASE_URL + "/bookings/" + bookingId;
//                restTemplate.delete(url);
//                System.out.println("Booking deleted successfully!");
//            } catch (HttpClientErrorException e) {
//                System.out.println("Failed to delete booking: " + e.getMessage());
//            } catch (Exception e) {
//                System.out.println("Error deleting booking: " + e.getMessage());
//            }
//        } else {
//            System.out.println("Deletion cancelled.");
//        }
//    }
//
//    private void registerForBooking() {
//        if (!"ATTENDEE".equals(currentUser.getUserType())) {
//            System.out.println("Only attendees can register for bookings.");
//            return;
//        }
//
//        System.out.println("\n===== Register for Booking =====");
//
//        // Get all bookings
//        String url = API_BASE_URL + "/bookings";
//        BookingDTO[] bookings = restTemplate.getForObject(url, BookingDTO[].class);
//
//        if (bookings == null || bookings.length == 0) {
//            System.out.println("No bookings available.");
//            return;
//        }
//
//        // Display bookings
//        displayBookings(bookings);
//
//        // Select booking to register for
//        System.out.print("Select booking to register for (1-" + bookings.length + "): ");
//        int bookingIndex = getIntInput() - 1;
//        if (bookingIndex < 0 || bookingIndex >= bookings.length) {
//            System.out.println("Invalid booking selection.");
//            return;
//        }
//
//        BookingDTO selectedBooking = bookings[bookingIndex];
//
//        // Check if there's space
//        if (selectedBooking.getCurrentAttendees() >= selectedBooking.getMaxCapacity()) {
//            System.out.println("This booking is already at maximum capacity.");
//            return;
//        }
//
//        // Register for booking
//        try {
//            String registerUrl = API_BASE_URL + "/bookings/" + selectedBooking.getId() + "/register/" + currentUser.getId();
//            restTemplate.postForObject(registerUrl, null, BookingDTO.class);
//            System.out.println("Successfully registered for the booking!");
//        } catch (HttpClientErrorException e) {
//            System.out.println("Failed to register for booking: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error registering for booking: " + e.getMessage());
//        }
//    }
//
//    private void unregisterFromBooking() {
//        if (!"ATTENDEE".equals(currentUser.getUserType())) {
//            System.out.println("Only attendees can unregister from bookings.");
//            return;
//        }
//
//        System.out.println("\n===== Unregister from Booking =====");
//
//        // Get attendee's bookings
//        String url = API_BASE_URL + "/bookings/attendee/" + currentUser.getId();
//        BookingDTO[] bookings = restTemplate.getForObject(url, BookingDTO[].class);
//
//        if (bookings == null || bookings.length == 0) {
//            System.out.println("You are not registered for any bookings.");
//            return;
//        }
//
//        // Display bookings
//        displayBookings(bookings);
//
//        // Select booking to unregister from
//        System.out.print("Select booking to unregister from (1-" + bookings.length + "): ");
//        int bookingIndex = getIntInput() - 1;
//        if (bookingIndex < 0 || bookingIndex >= bookings.length) {
//            System.out.println("Invalid booking selection.");
//            return;
//        }
//
//        BookingDTO selectedBooking = bookings[bookingIndex];
//
//        // Unregister from booking
//        try {
//            String unregisterUrl = API_BASE_URL + "/bookings/" + selectedBooking.getId() + "/unregister/" + currentUser.getId();
//            restTemplate.postForObject(unregisterUrl, null, BookingDTO.class);
//            System.out.println("Successfully unregistered from the booking!");
//        } catch (HttpClientErrorException e) {
//            System.out.println("Failed to unregister from booking: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error unregistering from booking: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Gets integer input from the user, handling invalid input.
//     *
//     * @return The entered integer, or 0 if input was invalid
//     */
//    private int getIntInput() {
//        try {
//            String input = scanner.nextLine().trim();
//            return Integer.parseInt(input);
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//    }
//}
