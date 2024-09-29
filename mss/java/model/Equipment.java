package model;

import java.sql.Date;

public class Equipment {
    private int id;
    private String code;
    private String name;
    private Date dateUse;
    private String origin;
    private int yom;
    private String qrCode;
    private int stageId;

    public Equipment() {
    }

    public Equipment(int id, String code, String name, Date dateUse, String origin, int yom, String qrCode, int stageId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dateUse = dateUse;
        this.origin = origin;
        this.yom = yom;
        this.qrCode = qrCode;
        this.stageId = stageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateUse() {
        return dateUse;
    }

    public void setDateUse(Date dateUse) {
        this.dateUse = dateUse;
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

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", dateUse=" + dateUse +
                ", origin='" + origin + '\'' +
                ", yom=" + yom +
                ", qrCode='" + qrCode + '\'' +
                ", stageId=" + stageId +
                '}';
    }
}
