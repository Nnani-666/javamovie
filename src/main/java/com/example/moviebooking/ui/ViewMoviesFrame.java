package com.example.moviebooking.ui;

import com.example.moviebooking.dao.MovieDAO;
import com.example.moviebooking.model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ViewMoviesFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewMoviesFrame() {
        setTitle("Available Movies");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        loadMovies();
    }

    private void initUI() {
        String[] columnNames = {"Movie ID", "Name", "Genre", "Price", "Available Seats"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadMovies() {
        try {
            List<Movie> movies = new MovieDAO().getAllMovies();
            for (Movie m : movies) {
                Object[] row = {
                        m.getMovieId(),
                        m.getMovieName(),
                        m.getGenre(),
                        String.format("Rs. %.2f", m.getPrice()),
                        m.getAvailableSeats()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading movies: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
