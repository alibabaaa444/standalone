package app.mappers;

import app.entities.Student;
import app.persistence.ConnectionPool;
import app.persistence.StudentMapper;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentMapperTest {
    private StudentMapper studentMapper;

    @BeforeAll
    public void setup() {
        ConnectionPool connectionPool = ConnectionPool.getInstance(
                "postgres", "postgres", "jdbc:postgresql://localhost:5432/standalone", "public");
        studentMapper = new StudentMapper(connectionPool);
    }

    @Test
    public void testGetAllStudents() throws SQLException {
        List<Student> students = studentMapper.getAllStudents();
        assertNotNull(students); // Ensure the result is not null
        assertTrue(students.size() > 0); // Ensure there are students in the database
    }

    @Test
    public void testAddStudent() throws SQLException {
        Student student = new Student("test.student@example.com", "Test Student", "123456789");
        studentMapper.addStudent(student);

        List<Student> students = studentMapper.getAllStudents();
        boolean found = students.stream().anyMatch(s -> s.getEmail().equals("test.student@example.com"));
        assertTrue(found); // Ensure the student was added
    }
}
