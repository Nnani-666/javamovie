# Movie Ticket Booking App

A clean, modular Java-based desktop application for booking movie tickets, built with a sci-fi theme.

## Technologies Used

- **Java (JDK 17)**: The core programming language used to build the logic.
- **Java Swing**: Used to create the Graphical User Interface (GUI) of the application (buttons, tables, forms, etc.).
- **SQLite Database**: Used as a zero-configuration, serverless local database (`movie_booking.db`) to persistently store movie details and booking records. It runs entirely from a local file.
- **JDBC (Java Database Connectivity)**: Used to connect the Java application with the SQLite database, execute SQL queries, and manage transactions.
- **Bash Scripting**: Custom scripts (`compile.sh` and `run.sh`) automate downloading dependencies (SQLite driver), compiling the code, and running the application seamlessly without needing an IDE like IntelliJ or Eclipse.

## Application Architecture

The project follows Object-Oriented Programming (OOP) principles and utilizes the **Data Access Object (DAO)** design pattern to separate concerns:

1. **Models (`com.example.moviebooking.model`)**: 
   - `Movie.java` & `Booking.java`
   - POJOs (Plain Old Java Objects) with private fields and getters/setters (Encapsulation) representing our database tables.
2. **DAOs (`com.example.moviebooking.dao`)**: 
   - `MovieDAO.java` & `BookingDAO.java`
   - These classes contain all the SQL queries (Insert, Select, Update, Delete) keeping database logic separate from the UI.
3. **Utilities (`com.example.moviebooking.util`)**: 
   - `DBConnection.java`
   - Manages the SQLite database connection using the Singleton pattern. It is also responsible for automatically generating the tables and populating sample Sci-Fi movies if the database is empty.
4. **User Interface (`com.example.moviebooking.ui`)**: 
   - `HomeFrame`, `ViewMoviesFrame`, `BookTicketFrame`, etc.
   - Contains the Java Swing `JFrame` classes that build the visual screens the user interacts with.

## Features

- **View Movies**: Browse a list of available sci-fi movies, their genres, prices (in Rs.), and available seat counts.
- **Book Ticket**: Select a movie from a dropdown, enter a customer name and the number of tickets. The app dynamically calculates the total cost. It validates seat availability, saves the booking to the database, and deducts the booked seats from the movie.
- **Cancel Booking**: Enter a booking ID to completely cancel a reservation. The app automatically restores the available seats for that specific movie.
- **Booking Summary**: Look up detailed information about a past booking using a booking ID.

## How to Run

This project is completely "zero-setup". You do not need to install a database server (like MySQL) or manually configure an IDE.

1. Open your terminal and navigate to the project folder.
2. Ensure you have execute permissions on the scripts:
   ```bash
   chmod +x run.sh compile.sh
   ```
3. Run the application:
   ```bash
   ./run.sh
   ```
*(Note: If the code needs to be recompiled after making changes, run `./compile.sh` first).*
