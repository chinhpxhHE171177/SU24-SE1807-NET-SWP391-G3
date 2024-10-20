package model;

public class Stages {
    private int id;
    private String name;
    private int lineId;

    public Stages() {
    }

    public Stages(int id, String name, int lineId) {
        this.id = id;
        this.name = name;
        this.lineId = lineId;
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

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    @Override
    public String toString() {
        return "Stages{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lineId=" + lineId +
                '}';
    }
}
