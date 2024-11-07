package com.example.qrcode;

public class Device_History {
    private int id;
    private String idCode;
    private String code;
    private String name;
    private String issue;
    private String stage;
    private String line;
    private String startTime;
    private String endTime;
    private String totalTime;
    private int recordCount;
    private String typeName;

    public Device_History() {
    }

    public Device_History(int id, String idCode, String code, String name, String stage, String typeName, String issue, String startTime, String endTime, String totalTime, int recordCount) {
        this.id = id;
        this.idCode = idCode;
        this.code = code;
        this.name = name;
        this.stage = stage;
        this.typeName = typeName;
        this.issue = issue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.recordCount = recordCount;
    }

    public Device_History(int id, String idCode, String code, String name, String issue, String stage, String line, String startTime, String endTime, String totalTime, int recordCount, String typeName) {
        this.id = id;
        this.idCode = idCode;
        this.code = code;
        this.name = name;
        this.issue = issue;
        this.stage = stage;
        this.line = line;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.recordCount = recordCount;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}