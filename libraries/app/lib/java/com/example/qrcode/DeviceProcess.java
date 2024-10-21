package com.example.qrcode;

import java.util.ArrayList;

public class DeviceProcess {
    String code;
    String name;
    String stage;
    ArrayList<String> issues;
    long startTime;

    DeviceProcess(String code, String name, ArrayList<String> issues, long startTime) {
        this.code = code;
        this.name = name;
        this.issues = issues;
        this.startTime = startTime;
    }

    public DeviceProcess(String code, String name, String stage, ArrayList<String> issues, long startTime) {
        this.code = code;
        this.name = name;
        this.stage = stage;
        this.issues = issues;
        this.startTime = startTime;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public ArrayList<String> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<String> issues) {
        this.issues = issues;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


}
