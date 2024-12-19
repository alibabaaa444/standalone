package app.persistence;

import app.entities.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingMapper {
    private final ConnectionPool connectionPool;

    public BookingMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void addBooking(Booking booking) {
        String sql = "INSERT INTO booking (booking_date, days, comment, booking_status, student_email, item) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(booking.getBookingDate()));
            ps.setInt(2, booking.getDays());
            ps.setString(3, booking.getComment());
            ps.setString(4, booking.getBookingStatus());
            ps.setString(5, booking.getStudentEmail());
            ps.setInt(6, booking.getItemId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding booking to database", e);
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate bookingDate = rs.getDate("booking_date").toLocalDate();
                int days = rs.getInt("days");
                String comment = rs.getString("comment");
                String bookingStatus = rs.getString("booking_status");
                String studentEmail = rs.getString("student_email");
                int itemId = rs.getInt("item");

                bookings.add(new Booking(id, bookingDate, days, comment, bookingStatus, studentEmail, itemId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bookings from database", e);
        }
        return bookings;
    }

    public void updateStatus(int bookingId, String newStatus) {
        String sql = "UPDATE booking SET booking_status = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating booking status", e);
        }
    }

}
