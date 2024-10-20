package model;

import java.sql.Timestamp;

public class ErrorHistory {
    private int id;
    private int equipmentId;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;
    private int duration;
    private int stageId;

    private String equipmentCode;
    private String equipmentName;
    private String stageName;
    private String lineName;
    private String departmentName;

    private int year;
    private int month;
    private int day;
    
    private int errorCount;

    public ErrorHistory() {
    }

    public ErrorHistory(int id, int equipmentId, String content, Timestamp startDate, Timestamp endDate, int stageId, int duration) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
        this.duration = duration;
    }

    public ErrorHistory(int id, int equipmentId, String content, Timestamp startDate, Timestamp endDate, int duration, int stageId, String equipmentCode, String equipmentName, String stageName, String lineName) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
        this.duration = duration;
        this.equipmentCode = equipmentCode;
        this.equipmentName = equipmentName;
        this.stageName = stageName;
        this.lineName = lineName;
    }

    public ErrorHistory(int equipmentId, String content, Timestamp startDate, Timestamp endDate, int stageId) {
        this.equipmentId = equipmentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
    }
    
//    public ErrorHistory(int equipmentId, String content, Timestamp startDate, Timestamp endDate, int stageId) {
//        this.equipmentId = equipmentId;
//        this.content = content;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.stageId = stageId;
//    }

    

    //    // Calculate the duration based on start and end date
//    private int calculateDuration() {
//        if (startDate != null && endDate != null) {
//            LocalDateTime startDateTime = startDate.toLocalDateTime();
//            LocalDateTime endDateTime = endDate.toLocalDateTime();
//            return (int) Duration.between(startDateTime, endDateTime).toMinutes();
//        } else {
//            return 0;  // Default to 0 if dates are not properly set
//        }
//    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
        //this.duration = duration();//
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
       // this.duration = calculateDuration();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getErrorCount() { // Correct getter
        return errorCount;
    }

    public void setErrorCount(int errorCount) { // Correct setter
        this.errorCount = errorCount;
    }

    @Override
    public String toString() {
        return "ErrorHistory{" +
                "id=" + id +
                ", equipmentId=" + equipmentId +
                ", content='" + content + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", stageId=" + stageId +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", stageName='" + stageName + '\'' +
                ", lineName='" + lineName + '\'' +
                ", errorCount=" + errorCount + // Fixed to correctly show count
                '}';
    }
}
