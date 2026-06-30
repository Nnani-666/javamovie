package com.example.moviebooking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    public HomeFrame() {
        setTitle("Movie Ticket Booking System");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        JButton viewBtn = new JButton("View Movies");
        JButton bookBtn = new JButton("Book Ticket");
        JButton cancelBtn = new JButton("Cancel Booking");
        JButton summaryBtn = new JButton("Booking Summary");
        JButton exitBtn = new JButton("Exit");

        viewBtn.addActionListener(e -> new ViewMoviesFrame().setVisible(true));
        bookBtn.addActionListener(e -> new BookTicketFrame().setVisible(true));
        cancelBtn.addActionListener(e -> new CancelBookingFrame().setVisible(true));
        summaryBtn.addActionListener(e -> new BookingSummaryFrame().setVisible(true));
        exitBtn.addActionListener(e -> System.exit(0));

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(viewBtn);
        panel.add(bookBtn);
        panel.add(cancelBtn);
        panel.add(summaryBtn);
        panel.add(exitBtn);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeFrame().setVisible(true));
    }
}
