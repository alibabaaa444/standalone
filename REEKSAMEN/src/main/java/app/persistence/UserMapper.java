package app.persistence;

import app.entities.User;

import java.sql.*;

public class UserMapper {
    private final ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO \"user\" (email, password, role) VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding user to database", e);
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    return new User(email, password, role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
