package model;

import java.sql.Date;

public class Devices {

    private String deviceid;
    private String linecode;
    private String devicename;
    private Date dateuse;
    private String origin;
    private int yom;
    private String qrcode;
    private String location;
    private String status;
    private int processId;
    private String processName;
    private String eName;

    public Devices() {
    }

    public Devices(String deviceid, String linecode, String devicename, Date dateuse, String origin, int yom, String qrcode, String location, String status, int processId, String processName) {
        this.deviceid = deviceid;
        this.linecode = linecode;
        this.devicename = devicename;
        this.dateuse = dateuse;
        this.origin = origin;
        this.yom = yom;
        this.qrcode = qrcode;
        this.location = location;
        this.status = status;
        this.processId = processId;
        this.processName = processName;
    }

    public Devices(String deviceid, String linecode, String devicename, Date dateuse, String origin, int yom, String location, String status, int processId) {
        this.deviceid = deviceid;
        this.linecode = linecode;
        this.devicename = devicename;
        this.dateuse = dateuse;
        this.origin = origin;
        this.yom = yom;
        this.location = location;
        this.status = status;
        this.processId = processId;
    }

    public Devices(String deviceid, String linecode, String devicename, Date dateuse, String origin, int yom, String qrcode, String location, String status, int processId) {
        this.deviceid = deviceid;
        this.linecode = linecode;
        this.devicename = devicename;
        this.dateuse = dateuse;
        this.origin = origin;
        this.yom = yom;
        this.qrcode = qrcode;
        this.location = location;
        this.status = status;
        this.processId = processId;
    }

    public Devices(String deviceid, String linecode, String devicename, Date dateuse, String origin, int yom, String qrcode, String location, String status, int processId, String processName, String eName) {
        this.deviceid = deviceid;
        this.linecode = linecode;
        this.devicename = devicename;
        this.dateuse = dateuse;
        this.origin = origin;
        this.yom = yom;
        this.qrcode = qrcode;
        this.location = location;
        this.status = status;
        this.processId = processId;
        this.processName = processName;
        this.eName = eName;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public Date getDateuse() {
        return dateuse;
    }

    public void setDateuse(Date dateuse) {
        this.dateuse = dateuse;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getYom() {
        return yom;
    }

    public void setYom(int yom) {
        this.yom = yom;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    @Override
    public String toString() {
        return "Devices{" +
                "deviceid='" + deviceid + '\'' +
                ", linecode='" + linecode + '\'' +
                ", devicename='" + devicename + '\'' +
                ", dateuse=" + dateuse +
                ", origin='" + origin + '\'' +
                ", yom=" + yom +
                ", qrcode='" + qrcode + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", processId=" + processId +
                ", processName='" + processName + '\'' +
                '}';
    }
}
