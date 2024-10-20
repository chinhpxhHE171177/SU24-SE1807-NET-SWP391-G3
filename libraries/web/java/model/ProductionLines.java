package model;

public class ProductionLines {
    private int id;
    private String name;
    private int roomId;

    public ProductionLines() {
    }

    public ProductionLines(int id, String name, int roomId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ProductionLines{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}
