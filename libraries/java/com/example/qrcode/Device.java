package com.example.qrcode;

public class Device {
    private int id;
    private String idCode;
    private String code;
    private String name;
    private String stage;
    private String issue;
    public Device() {
    }

    public Device(int id, String idCode, String code, String name, String stage, String issue) {
        this.id = id;
        this.idCode = idCode;
        this.code = code;
        this.name = name;
        this.stage = stage;
        this.issue = issue;
    }

    public Device(int id, String idCode, String code, String name, String issue) {
        this.id = id;
        this.idCode = idCode;
        this.code = code;
        this.name = name;
        this.issue = issue;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}