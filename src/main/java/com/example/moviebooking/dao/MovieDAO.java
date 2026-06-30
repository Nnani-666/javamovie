package com.example.moviebooking.dao;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MovieDAO handles all database operations related to Movies.
 * Implements IMovieDAO to demonstrate Abstraction.
 */
public class MovieDAO implements IMovieDAO {

    /**
     * Retrieves a list of all available movies from the database.
     * @return A list of Movie objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<Movie> getAllMovies() throws SQLException {
        String sql = "SELECT movie_id, movie_name, genre, price, available_seats FROM movies";
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Movie m = new Movie();
                m.setMovieId(rs.getInt("movie_id"));
                m.setMovieName(rs.getString("movie_name"));
                m.setGenre(rs.getString("genre"));
                m.setPrice(rs.getDouble("price"));
                m.setAvailableSeats(rs.getInt("available_seats"));
                movies.add(m);
            }
        }
        return movies;
    }

    /**
     * Retrieves a specific movie by its ID.
     * @param movieId The unique identifier of the movie.
     * @return The Movie object if found, otherwise null.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public Movie getMovieById(int movieId) throws SQLException {
        String sql = "SELECT movie_id, movie_name, genre, price, available_seats FROM movies WHERE movie_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Movie m = new Movie();
                    m.setMovieId(rs.getInt("movie_id"));
                    m.setMovieName(rs.getString("movie_name"));
                    m.setGenre(rs.getString("genre"));
                    m.setPrice(rs.getDouble("price"));
                    m.setAvailableSeats(rs.getInt("available_seats"));
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * Updates the available seats for a given movie.
     * @param movieId The unique identifier of the movie.
     * @param newAvailableSeats The new seat count to be updated.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void updateAvailableSeats(int movieId, int newAvailableSeats) throws SQLException {
        String sql = "UPDATE movies SET available_seats = ? WHERE movie_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newAvailableSeats);
            stmt.setInt(2, movieId);
            stmt.executeUpdate();
        }
    }
}
