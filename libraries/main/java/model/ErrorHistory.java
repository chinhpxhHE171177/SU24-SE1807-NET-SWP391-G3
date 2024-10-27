package model;

import java.sql.Timestamp;

public class ErrorHistory {
    private int id;
    private int equipmentId;
    private String content;
    private Timestamp startDate;
    private String startTime;
    private Timestamp endDate;
    private double duration;
    private int stageId;

    private String equipmentCode;
    private String equipmentName;
    private String stageName;
    private String lineName;
    private String departmentName;

    private int year;
    private int month;
    private int day;
    
    private double longTime;
    private double shortTime;
    
    private int errorCount;
    private int longCount;
    private int shortCount;
    private double lossRate;
    private String shiftName;
    private double shortStopPercentage;

    public ErrorHistory() {
    }

    public ErrorHistory(int id, int equipmentId, String content, Timestamp startDate, Timestamp endDate, int stageId, int duration) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
        this.duration = duration;
    }

    public ErrorHistory(int id, int equipmentId, String content, Timestamp startDate, Timestamp endDate, int duration, int stageId, String equipmentCode, String equipmentName, String stageName, String lineName) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
        this.duration = duration;
        this.equipmentCode = equipmentCode;
        this.equipmentName = equipmentName;
        this.stageName = stageName;
        this.lineName = lineName;
    }

    public ErrorHistory(int equipmentId, String content, Timestamp startDate, Timestamp endDate, int stageId) {
        this.equipmentId = equipmentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
    }
    
//    public ErrorHistory(int equipmentId, String content, Timestamp startDate, Timestamp endDate, int stageId) {
//        this.equipmentId = equipmentId;
//        this.content = content;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.stageId = stageId;
//    }

    

    //    // Calculate the duration based on start and end date
//    private int calculateDuration() {
//        if (startDate != null && endDate != null) {
//            LocalDateTime startDateTime = startDate.toLocalDateTime();
//            LocalDateTime endDateTime = endDate.toLocalDateTime();
//            return (int) Duration.between(startDateTime, endDateTime).toMinutes();
//        } else {
//            return 0;  // Default to 0 if dates are not properly set
//        }
//    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
        //this.duration = duration();//
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
       // this.duration = calculateDuration();
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getErrorCount() { // Correct getter
        return errorCount;
    }

    public void setErrorCount(int errorCount) { // Correct setter
        this.errorCount = errorCount;
    }

    public double getLongTime() {
		return longTime;
	}

	public void setLongTime(double longTime) {
		this.longTime = longTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public double getShortTime() {
		return shortTime;
	}

	public void setShortTime(double shortTime) {
		this.shortTime = shortTime;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	public int getLongCount() {
		return longCount;
	}

	public void setLongCount(int longCount) {
		this.longCount = longCount;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public int getShortCount() {
		return shortCount;
	}

	public void setShortCount(int shortCount) {
		this.shortCount = shortCount;
	}

	public double getLossRate() {
		return lossRate;
	}

	public void setLossRate(double lossRate) {
		this.lossRate = lossRate;
	}
	
	public double getShortStopPercentage() {
		return shortStopPercentage;
	}

	public void setShortStopPercentage(double shortStopPercentage) {
		this.shortStopPercentage = shortStopPercentage;
	}

	@Override
	public String toString() {
		return "ErrorHistory [id=" + id + ", equipmentId=" + equipmentId + ", content=" + content + ", startDate="
				+ startDate + ", startTime=" + startTime + ", endDate=" + endDate + ", duration=" + duration
				+ ", stageId=" + stageId + ", equipmentCode=" + equipmentCode + ", equipmentName=" + equipmentName
				+ ", stageName=" + stageName + ", lineName=" + lineName + ", departmentName=" + departmentName
				+ ", year=" + year + ", month=" + month + ", day=" + day + ", longTime=" + longTime + ", shortTime="
				+ shortTime + ", errorCount=" + errorCount + ", longCount=" + longCount + ", shortCount=" + shortCount
				+ ", lossRate=" + lossRate + "]";
	}


}
