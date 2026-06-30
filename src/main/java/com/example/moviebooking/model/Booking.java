package com.example.moviebooking.model;

import java.time.LocalDateTime;

/**
 * Booking Model Class.
 * Demonstrates Encapsulation by hiding internal state (private fields)
 * and requiring all interaction to be performed through an object's public methods (getters/setters).
 */
public class Booking {
    private int bookingId;
    private String customerName;
    private int movieId;
    private int tickets;
    private double totalAmount;
    private LocalDateTime bookingDate;

    public Booking() {}

    public Booking(int bookingId, String customerName, int movieId, int tickets, double totalAmount, LocalDateTime bookingDate) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.movieId = movieId;
        this.tickets = tickets;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public int getTickets() { return tickets; }
    public void setTickets(int tickets) { this.tickets = tickets; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
}
