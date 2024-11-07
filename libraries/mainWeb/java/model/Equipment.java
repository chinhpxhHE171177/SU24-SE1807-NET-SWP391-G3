package model;

import java.sql.Date;

/**
 * 
 */
public class Equipment {
    private int id;
    private String code;
    private String name;
    private Date dateUse;
    private String origin;
    private int yom;
    private String qrCode;
    private int stageId;
    private String stageName;
    private String lineName;
    private String issue;
    private String idCode;

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
    
//    public Equipment(int id, String code, String name, Date dateUse, String origin, int yom, String qrCode, int stageId, String stageName, String lineName) {
//        this.id = id;
//        this.code = code;
//        this.name = name;
//        this.dateUse = dateUse;
//        this.origin = origin;
//        this.yom = yom;
//        this.qrCode = qrCode;
//        this.stageId = stageId;
//        this.stageName = stageName;
//		this.lineName = lineName;
//    }
    
    public Equipment(int id, String code, String name, Date dateUse, String origin, int yom, String qrCode, int stageId, String issue,
    	    String stageName, String lineName) {
    	    this.id = id;
    	    this.code = code;
    	    this.name = name;
    	    this.dateUse = dateUse;
    	    this.origin = origin;
    	    this.yom = yom;
    	    this.qrCode = qrCode;
    	    this.stageId = stageId;
    	    this.issue = issue;
    	    this.stageName = stageName;
    	    this.lineName = lineName;
    	}


	public Equipment(int id, String code, String name, Date dateUse, String origin, int yom, String qrCode, int stageId,
			String stageName, String lineName, String issue, String idCode) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.dateUse = dateUse;
		this.origin = origin;
		this.yom = yom;
		this.qrCode = qrCode;
		this.stageId = stageId;
		this.stageName = stageName;
		this.lineName = lineName;
		this.issue = issue;
		this.idCode = idCode;
	}
	
	public Equipment(int id, String code, String name, Date dateUse, String origin, int yom, String qrCode, int stageId,
			String issue, String idCode) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.dateUse = dateUse;
		this.origin = origin;
		this.yom = yom;
		this.qrCode = qrCode;
		this.stageId = stageId;
		this.issue = issue;
		this.idCode = idCode;
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

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	@Override
	public String toString() {
		return "Equipment [id=" + id + ", code=" + code + ", name=" + name + ", dateUse=" + dateUse + ", origin="
				+ origin + ", yom=" + yom + ", qrCode=" + qrCode + ", stageId=" + stageId + ", stage=" + stageName
				+ ", lineName=" + lineName + ", issue=" + issue + ", idCode=" + idCode + "]";
	}

}
