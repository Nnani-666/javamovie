package com.example.moviebooking.dao;

import com.example.moviebooking.model.Booking;
import com.example.moviebooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingDAO {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public int createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (customer_name, movie_id, tickets, total_amount, booking_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, booking.getCustomerName());
            stmt.setInt(2, booking.getMovieId());
            stmt.setInt(3, booking.getTickets());
            stmt.setDouble(4, booking.getTotalAmount());
            stmt.setString(5, booking.getBookingDate().format(DATE_TIME_FORMATTER));
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        }
    }

    public Booking getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT booking_id, customer_name, movie_id, tickets, total_amount, booking_date FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("booking_id"));
                    b.setCustomerName(rs.getString("customer_name"));
                    b.setMovieId(rs.getInt("movie_id"));
                    b.setTickets(rs.getInt("tickets"));
                    b.setTotalAmount(rs.getDouble("total_amount"));
                    b.setBookingDate(LocalDateTime.parse(rs.getString("booking_date"), DATE_TIME_FORMATTER));
                    return b;
                }
            }
        }
        return null;
    }

    public boolean cancelBooking(int bookingId) throws SQLException {
        // Retrieve booking to know how many seats to restore
        Booking booking = getBookingById(bookingId);
        if (booking == null) {
            return false;
        }
        // Delete booking
        String deleteSql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, bookingId);
            int affected = deleteStmt.executeUpdate();
            if (affected == 0) {
                return false;
            }
        }
        // Restore seats in movies table
        String updateSeatsSql = "UPDATE movies SET available_seats = available_seats + ? WHERE movie_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSeatsSql)) {
            updateStmt.setInt(1, booking.getTickets());
            updateStmt.setInt(2, booking.getMovieId());
            updateStmt.executeUpdate();
        }
        return true;
    }
}
