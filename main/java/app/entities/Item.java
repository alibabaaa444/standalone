package app.entities;

public class Item {
    private int id;
    private String itemName;
    private String description;
    private String roomNumber;

    public Item(int id, String itemName, String description, String roomNumber) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
