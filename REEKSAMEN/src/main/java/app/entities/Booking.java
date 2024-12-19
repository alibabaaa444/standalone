package app.entities;

import java.time.LocalDate;

public class Booking {
    private int id;
    private LocalDate bookingDate;
    private int days;
    private String comment;
    private String bookingStatus;
    private String studentEmail;
    private int itemId;

    public Booking(int id, LocalDate bookingDate, int days, String comment, String bookingStatus, String studentEmail, int itemId) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.days = days;
        this.comment = comment;
        this.bookingStatus = bookingStatus;
        this.studentEmail = studentEmail;
        this.itemId = itemId;
    }

    public Booking(LocalDate bookingDate, int days, String comment, String bookingStatus, String studentEmail, int itemId) {
        this.bookingDate = bookingDate;
        this.days = days;
        this.comment = comment;
        this.bookingStatus = bookingStatus;
        this.studentEmail = studentEmail;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public int getDays() {
        return days;
    }

    public String getComment() {
        return comment;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public int getItemId() {
        return itemId;
    }
}
