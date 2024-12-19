package app.controllers;

import app.entities.Booking;
import app.persistence.BookingMapper;
import app.persistence.ItemMapper;
import app.persistence.StudentMapper;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.List;

public class BookingController {
    private final BookingMapper bookingMapper;
    private final StudentMapper studentMapper;
    private final ItemMapper itemMapper;

    public BookingController(BookingMapper bookingMapper, StudentMapper studentMapper, ItemMapper itemMapper) {
        this.bookingMapper = bookingMapper;
        this.studentMapper = studentMapper;
        this.itemMapper = itemMapper;
    }

    public void getAllBookings(Context ctx) {
        List<Booking> bookings = bookingMapper.getAllBookings();
        ctx.attribute("bookingList", bookings);
        ctx.render("bookings.html");
    }

    public void showAddBookingPage(Context ctx) {
        ctx.attribute("students", studentMapper.getAllStudents());
        ctx.attribute("items", itemMapper.getAllItems());
        ctx.render("addBooking.html");
    }

    public void addBooking(Context ctx) {
        String studentEmail = ctx.formParam("studentEmail");
        int itemId = Integer.parseInt(ctx.formParam("itemId"));
        LocalDate bookingDate = LocalDate.parse(ctx.formParam("bookingDate"));
        int days = Integer.parseInt(ctx.formParam("days"));
        String comment = ctx.formParam("comment");

        Booking booking = new Booking(bookingDate, days, comment, "in use", studentEmail, itemId);
        bookingMapper.addBooking(booking);

        ctx.redirect("/bookings");
    }

    public void updateBookingStatus(Context ctx) {
        int bookingId = Integer.parseInt(ctx.formParam("bookingId"));
        String newStatus = ctx.formParam("status");
        bookingMapper.updateStatus(bookingId, newStatus);
        ctx.redirect("/bookings");
    }


}
