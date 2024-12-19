package app.integration;

import app.entities.Booking;
import app.entities.Item;
import app.entities.Student;
import app.persistence.BookingMapper;
import app.persistence.ConnectionPool;
import app.persistence.ItemMapper;
import app.persistence.StudentMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingIntegrationTest {

    private BookingMapper bookingMapper;
    private StudentMapper studentMapper;
    private ItemMapper itemMapper;

    @BeforeAll
    public void setup() {
        ConnectionPool connectionPool = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/standalone",
                "public"
        );
        bookingMapper = new BookingMapper(connectionPool);
        studentMapper = new StudentMapper(connectionPool);
        itemMapper = new ItemMapper(connectionPool);
    }

    @Test
    public void testAddBooking() throws SQLException {
        // Add test student
        Student student = new Student("test.student@example.com", "Test Student", "123456789");
        studentMapper.addStudent(student);

        // Add test item
        Item item = new Item(1001, "Test Item", "Test Description", "101");

        // Add booking
        Booking booking = new Booking(
                LocalDate.now(), // bookingDate
                5,               // days
                "Test Comment",  // comment
                "Pending",       // bookingStatus
                student.getEmail(), // studentEmail
                item.getId()     // itemId
        );
        bookingMapper.addBooking(booking);

        // Retrieve bookings and verify the added booking
        List<Booking> bookings = bookingMapper.getAllBookings();
        boolean found = bookings.stream()
                .anyMatch(b -> b.getStudentEmail().equals(student.getEmail())
                        && b.getItemId() == item.getId()
                        && b.getComment().equals("Test Comment")
                        && b.getBookingStatus().equals("Pending"));
        assertTrue(found); // Ensure the booking was added correctly
    }
}
