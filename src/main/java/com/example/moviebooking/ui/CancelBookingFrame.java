package com.example.moviebooking.ui;

import com.example.moviebooking.dao.BookingDAO;
import com.example.moviebooking.dao.MovieDAO;
import com.example.moviebooking.model.Booking;
import com.example.moviebooking.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CancelBookingFrame extends JFrame {
    private JTextField txtBookingId;
    private JButton btnCancel;

    private BookingDAO bookingDAO = new BookingDAO();
    private MovieDAO movieDAO = new MovieDAO();

    public CancelBookingFrame() {
        setTitle("Cancel Booking");
        setSize(350, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Booking ID:"), gbc);
        gbc.gridx = 1;
        txtBookingId = new JTextField();
        txtBookingId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != java.awt.event.KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        add(txtBookingId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        btnCancel = new JButton("Cancel Booking");
        add(btnCancel, gbc);

        btnCancel.addActionListener(e -> cancelBooking());
    }

    private void cancelBooking() {
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
            boolean success = bookingDAO.cancelBooking(bookingId);
            if (success) {
                // restore seats already handled in DAO
                JOptionPane.showMessageDialog(this, "Booking cancelled and seats restored.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showError("Failed to cancel booking.");
            }
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
