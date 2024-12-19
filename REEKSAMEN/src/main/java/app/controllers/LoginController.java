package app.controllers;

import app.entities.Student;
import app.entities.User;
import app.persistence.StudentMapper;
import app.persistence.UserMapper;
import io.javalin.http.Context;

public class LoginController {
    private final UserMapper userMapper;
    private final StudentMapper studentMapper;

    public LoginController(UserMapper userMapper, StudentMapper studentMapper) {
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
    }

    public void showLoginPage(Context ctx) {
        ctx.render("login.html");
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = userMapper.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            ctx.sessionAttribute("currentUser", user);
            ctx.redirect("/");
        } else {
            ctx.redirect("/login?error=true");
        }
    }

    public void showRegisterPage(Context ctx) {
        ctx.render("register.html");
    }

    public void register(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String role = ctx.formParam("role");

        User user = new User(email, password, role);
        userMapper.addUser(user);

        if ("student".equalsIgnoreCase(role)) {
            String name = email.split("@")[0];
            String phone = "1234567890";
            Student student = new Student(email, name, phone);
            studentMapper.addStudent(student);
        }

        ctx.redirect("/login");
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/");
    }
}
