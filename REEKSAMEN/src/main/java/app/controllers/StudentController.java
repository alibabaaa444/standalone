package app.controllers;

import app.entities.Student;
import app.persistence.StudentMapper;
import io.javalin.http.Context;

import java.util.List;

public class StudentController {
    private final StudentMapper studentMapper;

    public StudentController(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public void getAllStudents(Context ctx) {
        List<Student> students = studentMapper.getAllStudents();
        ctx.attribute("studentList", students);
        ctx.render("students.html");
    }

    public void addStudent(Context ctx) {
        String email = ctx.formParam("email");
        String name = ctx.formParam("name");
        String phone = ctx.formParam("phone");

        if (email != null && name != null && phone != null) {
            Student student = new Student(email, name, phone);
            studentMapper.addStudent(student);
        }

        ctx.redirect("/students");
    }
}
