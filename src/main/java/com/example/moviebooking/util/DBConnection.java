package com.example.moviebooking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:movie_booking.db";
    private static Connection connection;

    private DBConnection() {
        // private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC Driver not found", e);
            }
            connection = DriverManager.getConnection(URL);
            initializeDatabase(connection);
        }
        return connection;
    }

    private static void initializeDatabase(Connection conn) throws SQLException {
        String createMoviesSql = "CREATE TABLE IF NOT EXISTS movies (" +
                "movie_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "movie_name TEXT NOT NULL, " +
                "genre TEXT, " +
                "price REAL NOT NULL, " +
                "available_seats INTEGER NOT NULL)";

        String createBookingsSql = "CREATE TABLE IF NOT EXISTS bookings (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_name TEXT NOT NULL, " +
                "movie_id INTEGER NOT NULL, " +
                "tickets INTEGER NOT NULL, " +
                "total_amount REAL NOT NULL, " +
                "booking_date TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE)";

        String countMoviesSql = "SELECT COUNT(*) FROM movies";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createMoviesSql);
            stmt.execute(createBookingsSql);
            
            // Insert sample data if empty
            try (java.sql.ResultSet rs = stmt.executeQuery(countMoviesSql)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.execute("INSERT INTO movies (movie_name, genre, price, available_seats) VALUES " +
                            "('Inception', 'Sci-Fi', 150.00, 100), " +
                            "('The Godfather', 'Crime', 120.00, 80), " +
                            "('Spirited Away', 'Animation', 100.00, 120), " +
                            "('Parasite', 'Thriller', 130.00, 90), " +
                            "('Interstellar', 'Sci-Fi', 140.00, 70)");
                }
            }
        }
    }
}
