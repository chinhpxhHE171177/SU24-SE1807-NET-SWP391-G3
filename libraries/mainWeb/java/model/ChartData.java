package model;

public class ChartData {
    private String date; // For Working Day
    private Integer hour; // For Hours
    private int totalDuration; // Total duration in minutes

    // Getters and setters
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Integer getHour() {
        return hour;
    }
    public void setHour(Integer hour) {
        this.hour = hour;
    }
    public int getTotalDuration() {
        return totalDuration;
    }
    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }
}

