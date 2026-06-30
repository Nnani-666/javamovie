package com.example.moviebooking.model;

public class Movie {
    private int movieId;
    private String movieName;
    private String genre;
    private double price;
    private int availableSeats;

    public Movie() {}

    public Movie(int movieId, String movieName, String genre, double price, int availableSeats) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    /**
     * Overridden toString method.
     * Demonstrates Polymorphism by overriding Object's toString method.
     * Useful for displaying the object directly in UI components like JComboBox.
     */
    @Override
    public String toString() {
        return movieName + " (" + availableSeats + " seats)";
    }
}
