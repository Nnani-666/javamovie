package com.example.moviebooking.ui;

import com.example.moviebooking.dao.BookingDAO;
import com.example.moviebooking.dao.MovieDAO;
import com.example.moviebooking.model.Booking;
import com.example.moviebooking.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class BookingSummaryFrame extends JFrame {
    private JTextField txtBookingId;
    private JButton btnSearch;
    private JTextArea txtResult;

    private BookingDAO bookingDAO = new BookingDAO();
    private MovieDAO movieDAO = new MovieDAO();

    public BookingSummaryFrame() {
        setTitle("Booking Summary");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5,5));
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Booking ID:"));
        txtBookingId = new JTextField(10);
        topPanel.add(txtBookingId);
        btnSearch = new JButton("Search");
        topPanel.add(btnSearch);
        add(topPanel, BorderLayout.NORTH);

        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtResult), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> fetchSummary());
    }

    private void fetchSummary() {
        int bookingId;
        try {
            bookingId = Integer.parseInt(txtBookingId.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid booking ID.");
            return;
        }
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                showError("Booking not found.");
                return;
            }
            Movie movie = movieDAO.getMovieById(booking.getMovieId());
            StringBuilder sb = new StringBuilder();
            sb.append("Booking ID: ").append(booking.getBookingId()).append('\n');
            sb.append("Customer Name: ").append(booking.getCustomerName()).append('\n');
            sb.append("Movie: ").append(movie != null ? movie.getMovieName() : "[unknown]").append('\n');
            sb.append("Tickets: ").append(booking.getTickets()).append('\n');
            sb.append("Total Amount: $").append(String.format("%.2f", booking.getTotalAmount())).append('\n');
            sb.append("Booking Date: ").append(booking.getBookingDate()).append('\n');
            txtResult.setText(sb.toString());
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
