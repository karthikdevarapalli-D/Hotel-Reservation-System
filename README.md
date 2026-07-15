# Hotel Reservation System

A complete, responsive Hotel Reservation System built as a Java Web Application using Spring Boot, Java 17, and Maven. This system operates without a traditional relational database, relying entirely on JSON file-based storage for fast and simple persistence.

## Features

- **Full-stack implementation**: Includes a robust REST API backend (Spring Boot) and a dynamic frontend built with vanilla HTML, CSS, and JavaScript.
- **Room Management**: View and manage available rooms.
- **Booking & Cancellation**: Make new reservations and cancel existing ones seamlessly.
- **Payment Simulation**: Process simulated payments as part of the booking flow.
- **Reporting Capabilities**: Built-in functionality for summarizing bookings and revenue.
- **File-based Persistence**: Uses `bookings.json`, `payments.json`, and `rooms.json` in the root directory to store all application data.

## Technologies Used

- **Backend**: Java 17, Spring Boot 3.2.0, Spring Web
- **Data Serialization**: Jackson Core & Databind (JSR-310 for dates)
- **Frontend**: Vanilla HTML5, CSS3, JavaScript
- **Build Tool**: Maven

## Prerequisites

- JDK 17 or higher
- Maven 3.6+

## Setup & Execution

1. Clone or navigate to the project directory:
   ```bash
   cd HotelReservationSystem
   ```

2. Run the application using the Spring Boot Maven Plugin:
   ```bash
   mvn spring-boot:run
   ```

3. Open your web browser and navigate to:
   ```
   http://localhost:8080
   ```

## Development

The project uses `spring-boot-devtools` for quick restarts during development. Simply make changes to your Java code or static resources, and the application will reload automatically when compiled.

## Project Structure

- `src/main/java`: Contains the Spring Boot backend REST controllers, service layers, and data models.
- `src/main/resources`: Contains application configuration (`application.properties`) and static frontend resources (HTML, CSS, JS).
- `rooms.json`, `bookings.json`, `payments.json`: File-based storage holding the application state.
