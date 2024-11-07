package model;

public class Rooms {

    private int id;
    private String name;
    private int depId;

    public Rooms() {
    }

    public Rooms(int id, String name, int depId) {
        this.id = id;
        this.name = name;
        this.depId = depId;
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

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", depId=" + depId +
                '}';
    }
}
