package com.example.qrcodenew;

import java.io.Serializable;

public class DeviceProcessing implements Serializable {
    private String idCode;
    private String code;
    private String name;
    private String issues;

    public DeviceProcessing(String idCode, String code, String name, String issues) {
        this.idCode = idCode;
        this.code = code;
        this.name = name;
        this.issues = issues;
    }

    public String getIdCode() {
        return idCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIssues() {
        return issues;
    }

    @Override
    public String toString() {
        return name + " - Issues: " + issues; // Hiển thị tên thiết bị và vấn đề
    }
}
