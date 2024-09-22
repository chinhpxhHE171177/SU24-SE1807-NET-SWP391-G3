package model;

public class DeviceError {
    private String deviceId;
    private int ETypeID;
    private String EtypeName;

    public DeviceError() {
    }

    public DeviceError(String deviceId, int ETypeID, String etypeName) {
        this.deviceId = deviceId;
        this.ETypeID = ETypeID;
        EtypeName = etypeName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getETypeID() {
        return ETypeID;
    }

    public void setETypeID(int ETypeID) {
        this.ETypeID = ETypeID;
    }

    public String getEtypeName() {
        return EtypeName;
    }

    public void setEtypeName(String etypeName) {
        EtypeName = etypeName;
    }

    @Override
    public String toString() {
        return "DeviceError{" +
                "deviceId='" + deviceId + '\'' +
                ", ETypeID=" + ETypeID +
                ", EtypeName='" + EtypeName + '\'' +
                '}';
    }
}
