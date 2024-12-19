package app.persistence;

import app.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMapper {
    private final ConnectionPool connectionPool;

    public StudentMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO student (email, name, phone) VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, student.getEmail());
            ps.setString(2, student.getName());
            ps.setString(3, student.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding student to database", e);
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT email, name, phone FROM student";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("email");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                students.add(new Student(email, name, phone));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching students from database", e);
        }
        return students;
    }
}
