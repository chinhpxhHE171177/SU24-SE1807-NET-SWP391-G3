package com.example.qrcode;

public class Record {
    private int id;
    private String deviceId;
    private String deviceName;
    private String commonIssues;
    private String startTime;
    private String endTime;
    private String totalTime;

    public Record(int id, String deviceId, String deviceName, String commonIssues, String startTime, String endTime, String totalTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.commonIssues = commonIssues;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getCommonIssues() {
        return commonIssues;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }
}
