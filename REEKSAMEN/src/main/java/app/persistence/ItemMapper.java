package app.persistence;

import app.entities.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    private final ConnectionPool connectionPool;

    public ItemMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, item_name, description, room_number FROM item";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String itemName = rs.getString("item_name");
                String description = rs.getString("description");
                String roomNumber = rs.getString("room_number");

                items.add(new Item(id, itemName, description, roomNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching items from database", e);
        }
        return items;
    }

}
