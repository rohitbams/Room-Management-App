package com.stacs.cs5031.p3.client.gui;
import javax.swing.*;

import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.service.OrganiserService;

import java.awt.Color;
import helper_classes.*;

public class ViewMyBookings {
  private OrganiserService organiserService;
  // private Organiser loggedInOrganiser;

  public ViewMyBookings(OrganiserService organiserService, Organiser organiser) {
    this.organiserService = organiserService;
    this.loggedInOrganiser = organiser;
  }

  public static void main(String[] args) {
    ViewMyBookings viewMyBookings = new ViewMyBookings();

    JFrame frame = new JFrame("My Bookings");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1143, 617);
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.decode("#f4c064"));

    JLabel titleField = new JLabel("My Bookings");
    titleField.setBounds(452, 9, 200, 40);
    titleField.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 32));
    titleField.setForeground(Color.decode("#000"));
    panel.add(titleField);

    
    JTable table = viewMyBookings.getTableOfBookings();
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(50, 70, 1000, 500); // Adjust the size and position as needed
    panel.add(scrollPane);
    
    frame.add(panel);
    frame.setVisible(true);
  }
  
  private JTable getTableOfBookings() {
    // Retrieve the list of bookings
    ArrayList<BookingDto> bookings = organiserService.getMyBookings();
  
    // Create a JTable to display the bookings
    String[] columnNames = { "Event", "Date & Time", "Organiser" };
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
  
    // Populate the table model with data from the ArrayList
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    for (BookingDto booking : bookings) {
      Object[] rowData = {
          booking.getName(), // get event name
          dateFormat.format(booking.getStartTime()),
          booking.getOrganiser().getName() // get organiser name
      };
      tableModel.addRow(rowData);
    }

    return new JTable(tableModel);
  }
}