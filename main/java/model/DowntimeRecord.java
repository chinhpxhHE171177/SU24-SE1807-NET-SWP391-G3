package model;

import java.sql.Timestamp;

public class DowntimeRecord {
    private int errorID;
    private int equipmentID;
    private String equipmentCode;
    private String equipmentName;
    private String origin;
    private String qrCode;
    private int yom;  // Year of Manufacture
    private String errorDescription;
    private Timestamp startTime;
    private Timestamp endTime;
    private int duration;  // Duration in minutes
    private String stageName;
    private String lineName;
    private int totalDowntime;
    private int errorCount;

    public DowntimeRecord() {
    }

    public DowntimeRecord(int equipmentID, String equipmentCode, String equipmentName, String origin, String qrCode, int yom, String errorDescription, Timestamp startTime, Timestamp endTime, int duration, String stageName, String lineName) {
        this.equipmentID = equipmentID;
        this.equipmentCode = equipmentCode;
        this.equipmentName = equipmentName;
        this.origin = origin;
        this.qrCode = qrCode;
        this.yom = yom;
        this.errorDescription = errorDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.stageName = stageName;
        this.lineName = lineName;
    }

    public DowntimeRecord(int errorID, int equipmentID, String equipmentCode, String equipmentName, String origin, String qrCode, int yom, String errorDescription, Timestamp startTime, Timestamp endTime, int duration, String stageName, String lineName) {
        this.errorID = errorID;
        this.equipmentID = equipmentID;
        this.equipmentCode = equipmentCode;
        this.equipmentName = equipmentName;
        this.origin = origin;
        this.qrCode = qrCode;
        this.yom = yom;
        this.errorDescription = errorDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.stageName = stageName;
        this.lineName = lineName;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getYom() {
        return yom;
    }

    public void setYom(int yom) {
        this.yom = yom;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getErrorID() {
        return errorID;
    }

    public void setErrorID(int errorID) {
        this.errorID = errorID;
    }

    // Getter và Setter cho totalDowntime
    public int getTotalDowntime() { return totalDowntime; }
    public void setTotalDowntime(int totalDowntime) { this.totalDowntime = totalDowntime; }

    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }

    @Override
    public String toString() {
        return "DowntimeRecord{" +
                "errorID=" + errorID +
                ", equipmentID=" + equipmentID +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", origin='" + origin + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", yom=" + yom +
                ", errorDescription='" + errorDescription + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", stageName='" + stageName + '\'' +
                ", lineName='" + lineName + '\'' +
                ", totalDowntime=" + totalDowntime +
                '}';
    }
}
