package model;

public class ProductionLines {
    private int id;
    private String name;
    private int roomId;
    
    private double longTime;
    private double shortTime;
    private double duration;
    private int count;
    
    private String departmentName;

    public ProductionLines() {
    }

    public ProductionLines(String name, String departmentName) {
		super();
		this.name = name;
		this.departmentName = departmentName;
	}

	public ProductionLines(int id, String name, int roomId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
    }

    
    public ProductionLines(int id, String name, double longTime, double shortTime, double duration, int count) {
		super();
		this.id = id;
		this.name = name;
		this.longTime = longTime;
		this.shortTime = shortTime;
		this.duration = duration;
		this.count = count;
	}

	public double getLongTime() {
		return longTime;
	}

	public void setLongTime(double longTime) {
		this.longTime = longTime;
	}

	public double getShortTime() {
		return shortTime;
	}

	public void setShortTime(double shortTime) {
		this.shortTime = shortTime;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
        return id;
    }

    public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

	@Override
	public String toString() {
		return "ProductionLines [id=" + id + ", name=" + name + ", roomId=" + roomId + ", longTime=" + longTime
				+ ", shortTime=" + shortTime + ", duration=" + duration + ", count=" + count + "]";
	}
  
}
