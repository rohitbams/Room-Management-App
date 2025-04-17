package com.stacs.cs5031.p3.client.gui.organiser_view;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.stacs.cs5031.p3.client.gui.helper_classes.*;
import com.stacs.cs5031.p3.server.dto.AttendeeDto;
import com.stacs.cs5031.p3.server.dto.BookingDto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyBookingsPage extends JFrame {
	private ArrayList<BookingDto> bookings;
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public MyBookingsPage(int organiserId) {
        // Set up the frame
        setTitle("My Bookings Page");
        setSize(1143, 617);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel();
        // mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.decode("#f4c064"));
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("My Bookings", JLabel.CENTER);
        titleLabel.setBounds(196, 219, 106, 17);
        titleLabel.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        titleLabel.setForeground(Color.decode("#000"));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Fetch bookings
        bookings = fetchBookings(organiserId);

        // Create the table panel
        JPanel tablePanel = createTablePanel();
        tablePanel.setBounds(50, 250, 1000, 300);
        mainPanel.add(tablePanel);

        // Add to frame
        add(mainPanel);
    }
    

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Initialize table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        // Add columns
        tableModel.addColumn("Event Name");
        tableModel.addColumn("Date & Time");
        tableModel.addColumn("Organiser Name");
        
        // Create and configure table
        bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingTable.getTableHeader().setReorderingAllowed(false);
        bookingTable.setAutoCreateRowSorter(true);
        
        // Populate table with data
        populateTable();
        
        // Add click handling for the table rows
        bookingTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bookingTable.getSelectedRow();
                if (row != -1) {
                    // Convert to model row if table is sorted
                    if (bookingTable.getRowSorter() != null) {
                        row = bookingTable.getRowSorter().convertRowIndexToModel(row);
                    }
                    
                    if (row < bookings.size()) {
                        BookingDto selectedBooking = bookings.get(row);
                        showBookingDetailsDialog(selectedBooking);
                    }
                }
            }
        });
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

	private void populateTable() {
		// Clear existing data
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}

		// Format for displaying the date/time
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		// Add rows to the table model from the BookingDto objects
		for (BookingDto booking : bookings) {
			String formattedDate = booking.getStartTime() != null ? dateFormat.format(booking.getStartTime()) : "N/A";

			tableModel.addRow(new Object[] {
					booking.getEventName(),
					formattedDate,
					booking.getOrganiserName()
			});
		}
	}
	
    private void showBookingDetailsDialog(BookingDto booking) {
        JDialog dialog = new JDialog(this);
        dialog.setTitle("Booking Details");
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Add event name as title
        JLabel titleLabel = new JLabel(booking.getEventName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Details panel with grid layout
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 8));
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10))));

        // Add all booking details
        addDetailRow(detailsPanel, "ID:", String.valueOf(booking.getId()));
        addDetailRow(detailsPanel, "Room ID:", String.valueOf(booking.getRoomId()));
        addDetailRow(detailsPanel, "Room Name:", booking.getRoomName());

        SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = booking.getStartTime() != null ? fullDateFormat.format(booking.getStartTime()) : "N/A";
        addDetailRow(detailsPanel, "Start Time:", startTimeStr);

        addDetailRow(detailsPanel, "Duration:", booking.getDuration() + " minutes");
        addDetailRow(detailsPanel, "Organiser ID:", String.valueOf(booking.getOrganiserId()));
        addDetailRow(detailsPanel, "Organiser Name:", booking.getOrganiserName());
        addDetailRow(detailsPanel, "Current Attendees:", String.valueOf(booking.getCurrentAttendees()));
        addDetailRow(detailsPanel, "Maximum Capacity:", String.valueOf(booking.getMaxCapacity()));

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Attendees section if available
        if (booking.getAttendees() != null && !booking.getAttendees().isEmpty()) {
            JPanel attendeesPanel = new JPanel(new BorderLayout(5, 5));
            attendeesPanel.setBorder(BorderFactory.createTitledBorder("Attendees"));

            DefaultListModel<String> attendeeModel = new DefaultListModel<>();
            for (AttendeeDto attendee : booking.getAttendees()) {
                attendeeModel.addElement(attendee.getName() + " (" + attendee.getId() + ")");
            }

            JList<String> attendeeList = new JList<>(attendeeModel);
            JScrollPane attendeeScroll = new JScrollPane(attendeeList);
            attendeeScroll.setPreferredSize(new Dimension(400, 150));
            attendeesPanel.add(attendeeScroll, BorderLayout.CENTER);

            mainPanel.add(attendeesPanel, BorderLayout.SOUTH);
        }

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");

        // Use your OnClickEventHelper for the close button
        OnClickEventHelper.setOnClickColor(closeButton, new Color(220, 220, 220),
                UIManager.getColor("Button.background"));

        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // Add button panel to dialog
        if (mainPanel.getComponentCount() > 2) {
            // If we have attendees, add at the very bottom
            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.add((Component) mainPanel.getComponent(2), BorderLayout.CENTER);
            southPanel.add(buttonPanel, BorderLayout.SOUTH);
            mainPanel.remove(2);
            mainPanel.add(southPanel, BorderLayout.SOUTH);
        } else {
            // Otherwise add directly to main panel
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
	// Helper method to add a row of details
	private void addDetailRow(JPanel panel, String label, String value) {
		panel.add(new JLabel(label, JLabel.RIGHT));
		panel.add(new JLabel(value, JLabel.LEFT));
	}

	// // Method to fetch bookings from the backend
    // private ArrayList<BookingDto> fetchBookings(int organiserId) {
    //     // Define the backend API URL
    //     String url = "http://localhost:8080/organiser/my-bookings/" + organiserId;
    //     RestTemplate restTemplate = new RestTemplate();
    //     try {
    //         ResponseEntity<BookingDto[]> response = restTemplate.getForEntity(url, BookingDto[].class);
    //         if (response.getStatusCode() == HttpStatus.OK) {
    //             // Convert the array to an ArrayList and return it
    //             BookingDto[] bookingsArray = response.getBody();
    //             return new ArrayList<>(List.of(bookingsArray));
    //         } else {
    //             // Handle non-OK responses
    //             System.err.println("Failed to fetch bookings. HTTP Status: " + response.getStatusCode());
    //             return new ArrayList<>();
    //         }
    //     } catch (Exception e) {
    //         // Handle exceptions (e.g., connection errors)
    //         System.err.println("Error fetching bookings: " + e.getMessage());
    //         return new ArrayList<>();
    //     }
    // }
    
    private ArrayList<BookingDto> fetchBookings(int organiserId) {
        // Mock data for testing
        ArrayList<BookingDto> mockBookings = new ArrayList<>();
        mockBookings.add(new BookingDto(1L, "Event A", 101L, "Room 1", new java.util.Date(), 60, 201L, "John Doe", null, 10, 50));
        mockBookings.add(new BookingDto(2L, "Event B", 102L, "Room 2", new java.util.Date(), 120, 202L, "Jane Smith", null, 20, 100));
        mockBookings.add(new BookingDto(3L, "Event C", 103L, "Room 3", new java.util.Date(), 90, 203L, "Alice Johnson", null, 15, 75));
        return mockBookings;
    }
	
	// For testing or standalone usage
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyBookingsPage page = new MyBookingsPage(123);
            page.setVisible(true);
        });
    }

}