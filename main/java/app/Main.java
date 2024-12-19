package app;

import app.config.ThymeleafConfig;
import app.controllers.*;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/standalone";
    private static final String DB = "public";

    public static void main(String[] args) {
        ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        // Initialize Mappers
        StudentMapper studentMapper = new StudentMapper(connectionPool);
        ItemMapper itemMapper = new ItemMapper(connectionPool);
        BookingMapper bookingMapper = new BookingMapper(connectionPool);
        UserMapper userMapper = new UserMapper(connectionPool);

        // Initialize Controllers
        StudentController studentController = new StudentController(studentMapper);
        ItemController itemController = new ItemController(itemMapper);
        BookingController bookingController = new BookingController(bookingMapper, studentMapper, itemMapper);
        LoginController loginController = new LoginController(userMapper, studentMapper);

        // Start Javalin Server
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);


        // Routes
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/students", studentController::getAllStudents);
        app.get("/items", itemController::getAllItems);
        app.get("/bookings", bookingController::getAllBookings);
        app.get("/addStudent", ctx -> ctx.render("addStudent.html"));
        app.post("/students/add", studentController::addStudent);
        app.get("/addBooking", bookingController::showAddBookingPage);
        app.post("/addBooking", bookingController::addBooking);

        app.get("/login", loginController::showLoginPage);
        app.post("/login", loginController::login);
        app.get("/register", loginController::showRegisterPage);
        app.post("/register", loginController::register);
        app.get("/logout", loginController::logout);
        app.post("/bookings/updateStatus", bookingController::updateBookingStatus);

    }
}
