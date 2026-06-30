package com.example.moviebooking.ui;

import com.example.moviebooking.dao.BookingDAO;
import com.example.moviebooking.dao.MovieDAO;
import com.example.moviebooking.model.Booking;
import com.example.moviebooking.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BookTicketFrame extends JFrame {
    private JTextField txtCustomerName;
    private JComboBox<Movie> cmbMovies;
    private JTextField txtTickets;
    private JLabel lblTotal;
    private JButton btnBook;

    private MovieDAO movieDAO = new MovieDAO();
    private BookingDAO bookingDAO = new BookingDAO();

    public BookTicketFrame() {
        setTitle("Book Ticket");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        loadMovies();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1;
        txtCustomerName = new JTextField();
        add(txtCustomerName, gbc);

        // Movie selection
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Movie:"), gbc);
        gbc.gridx = 1;
        cmbMovies = new JComboBox<>();
        cmbMovies.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            return new JLabel(value != null ? value.getMovieName() + " (" + value.getAvailableSeats() + " seats)" : "");
        });
        add(cmbMovies, gbc);

        // Tickets
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Tickets:"), gbc);
        gbc.gridx = 1;
        txtTickets = new JTextField();
        add(txtTickets, gbc);

        // Total amount
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Total Amount:"), gbc);
        gbc.gridx = 1;
        lblTotal = new JLabel("$0.00");
        add(lblTotal, gbc);

        // Book button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        btnBook = new JButton("Book");
        add(btnBook, gbc);

        // Listeners
        txtTickets.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateTotal();
            }
        });
        cmbMovies.addActionListener(e -> updateTotal());
        btnBook.addActionListener(e -> performBooking());
    }

    private void loadMovies() {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            DefaultComboBoxModel<Movie> model = new DefaultComboBoxModel<>();
            for (Movie m : movies) {
                model.addElement(m);
            }
            cmbMovies.setModel(model);
        } catch (SQLException ex) {
            showError("Failed to load movies: " + ex.getMessage());
        }
    }

    private void updateTotal() {
        Movie selected = (Movie) cmbMovies.getSelectedItem();
        if (selected == null) {
            lblTotal.setText("$0.00");
            return;
        }
        int tickets = 0;
        try {
            tickets = Integer.parseInt(txtTickets.getText().trim());
        } catch (NumberFormatException ignored) {}
        double total = tickets * selected.getPrice();
        lblTotal.setText(String.format("$%.2f", total));
    }

    private void performBooking() {
        String customer = txtCustomerName.getText().trim();
        if (customer.isEmpty()) {
            showError("Customer name cannot be empty.");
            return;
        }
        Movie movie = (Movie) cmbMovies.getSelectedItem();
        if (movie == null) {
            showError("No movie selected.");
            return;
        }
        int tickets;
        try {
            tickets = Integer.parseInt(txtTickets.getText().trim());
        } catch (NumberFormatException e) {
            showError("Ticket count must be a number.");
            return;
        }
        if (tickets <= 0) {
            showError("Ticket count must be greater than zero.");
            return;
        }
        if (tickets > movie.getAvailableSeats()) {
            showError("Not enough seats available.");
            return;
        }
        double total = tickets * movie.getPrice();
        Booking booking = new Booking();
        booking.setCustomerName(customer);
        booking.setMovieId(movie.getMovieId());
        booking.setTickets(tickets);
        booking.setTotalAmount(total);
        booking.setBookingDate(LocalDateTime.now());
        try {
            int bookingId = bookingDAO.createBooking(booking);
            // update seats
            movieDAO.updateAvailableSeats(movie.getMovieId(), movie.getAvailableSeats() - tickets);
            JOptionPane.showMessageDialog(this,
                    "Booking successful! ID: " + bookingId,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
