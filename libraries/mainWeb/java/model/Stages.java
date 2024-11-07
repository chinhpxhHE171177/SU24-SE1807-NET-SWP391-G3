package model;

public class Stages {
    private int id;
    private String name;
    private int lineId; 
    
    private double longTime;
    private double shortTime;
    private double duration;
    private int count;

    public Stages() {
    }

    public Stages(int id, String name, int lineId) {
        this.id = id;
        this.name = name;
        this.lineId = lineId;
    }

    public Stages(int id, String name, int lineId, double longTime, double shortTime, double duration, int count) {
		super();
		this.id = id;
		this.name = name;
		this.lineId = lineId;
		this.longTime = longTime;
		this.shortTime = shortTime;
		this.duration = duration;
		this.count = count;
	}

    
	public Stages(int id, String name, double longTime, double shortTime, double duration, int count) {
		super();
		this.id = id;
		this.name = name;
		this.longTime = longTime;
		this.shortTime = shortTime;
		this.duration = duration;
		this.count = count;
	}

	public int getId() {
        return id;
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

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
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

	@Override
	public String toString() {
		return "Stages [id=" + id + ", name=" + name + ", lineId=" + lineId + ", longTime=" + longTime + ", shortTime="
				+ shortTime + ", duration=" + duration + ", count=" + count + "]";
	}
}
