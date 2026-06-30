package com.example.moviebooking.dao;

import com.example.moviebooking.model.Movie;

import java.sql.SQLException;
import java.util.List;

/**
 * IMovieDAO Interface.
 * Demonstrates Abstraction in Object-Oriented Programming (OOP).
 * This interface defines the contract for any class that handles movie data operations.
 */
public interface IMovieDAO {
    
    /**
     * Retrieves all movies from the database.
     * @return List of Movie objects.
     * @throws SQLException if a database error occurs.
     */
    List<Movie> getAllMovies() throws SQLException;

    /**
     * Retrieves a specific movie by its ID.
     * @param movieId The ID of the movie.
     * @return Movie object, or null if not found.
     * @throws SQLException if a database error occurs.
     */
    Movie getMovieById(int movieId) throws SQLException;

    /**
     * Updates the number of available seats for a movie.
     * @param movieId The ID of the movie.
     * @param newAvailableSeats The new seat count.
     * @throws SQLException if a database error occurs.
     */
    void updateAvailableSeats(int movieId, int newAvailableSeats) throws SQLException;
}
